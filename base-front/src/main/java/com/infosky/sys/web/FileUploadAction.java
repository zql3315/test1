package com.infosky.sys.web;

import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.infosky.common.util.PropertiesConfig;
import com.infosky.common.util.file.FileUtil;
import com.infosky.common.util.image.ImageUtils;
import com.infosky.common.util.image.ImgCutUtil;

/**
 * @author n004881
 * 
 *         文件上传控制中心
 * 
 */
@Controller
public class FileUploadAction {

    /**
     * 上传图片文件
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/fileUpload/image")
    @ResponseBody
    public Map<String, Object> imageUpload(HttpServletRequest request) {
        Map<String, Object> model = new HashMap<String, Object>();
        FileInputStream fis = null;
        ImageInputStream iis = null;
        model.put("result", false);
        model.put("msg", "上传失败");

        try {
            //转型为MultipartHttpRequest：
            String folderName = request.getParameter("folderName");
            if (StringUtils.isNotBlank(folderName)) {
                folderName = folderName + "/";
            } else {
                folderName = "";
            }

            String fileType = request.getParameter("type") == null ? "img" : request.getParameter("type");

            //创建一个通用的多部分解析器
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
            // 判断 request 是否有文件上传,即多部分请求
            if (multipartResolver.isMultipart(request)) {
                MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;

                String relativeFilePath = getRelativeFilePath(folderName, "images");
                String path = createFilePath(request, relativeFilePath);

                Iterator<String> iter = multipartRequest.getFileNames();

                while (iter.hasNext()) {
                    //取得上传文件
                    String fileName = iter.next();
                    MultipartFile file = multipartRequest.getFile(fileName);

                    if (!checkFileSize(file, model)) {
                        return model;
                    }
                    //源文件名
                    String originalFilename = file.getOriginalFilename();
                    //文件类型白名单
                    String whitelist_mime = (String) (request.getAttribute("whitelist_mime") != null ? request.getAttribute("whitelist_mime") : request.getParameter("whitelist_mime"));
                    if (StringUtils.isNotBlank(whitelist_mime) && !whitelist_mime.contains(file.getContentType())) {
                        model.put("msg", "请上传正确的文件类型：" + whitelist_mime);
                        break;
                    }

                    long time = new Date().getTime();
                    originalFilename = time + originalFilename.substring(originalFilename.lastIndexOf("."));

                    if (fileType.equals("img")) {
                        iis = ImageIO.createImageInputStream(file.getInputStream());
                        Iterator<ImageReader> imageReaderIte = ImageIO.getImageReaders(iis);
                        if (!imageReaderIte.hasNext()) {
                            // 文件不是图片
                            model.put("msg", "这不是一个图片");
                            break;
                        }
                        disposeImg(request, file, model, originalFilename, path);
                    } else {
                        model.put("msg", "非法文件");
                        break;
                    }
                    if ((boolean) model.get("result")) {
                        model.put("fileUrl", getRelativeFilePath(folderName, "images") + originalFilename);
                    }

                    break;
                }
            } else {
                model.put("msg", "please chose  a file！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (iis != null) {
                try {
                    iis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return model;
    }

    /**
     * 上传视频文件 
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/fileUpload/video")
    @ResponseBody
    public Map<String, Object> videoUpload(HttpServletRequest request) {
        Map<String, Object> model = new HashMap<String, Object>();
        FileInputStream fis = null;
        ImageInputStream iis = null;
        model.put("result", false);
        model.put("msg", "上传失败");
        try {
            // 转型为MultipartHttpRequest：
            String folderName = "";
            folderName = request.getParameter("folderName");
            String fileType = request.getParameter("type") == null ? "img" : request.getParameter("type");
            if (StringUtils.isNotBlank(folderName)) {
                folderName = folderName + "/";
            } else {
                folderName = "";
            }
            // 创建一个通用的多部分解析器
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
            // 判断 request 是否有文件上传,即多部分请求
            if (multipartResolver.isMultipart(request)) {
                MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
                String relativeFilePath = getRelativeFilePath(folderName, "video");
                String path = createFilePath(request, relativeFilePath);
                Iterator<String> iter = multipartRequest.getFileNames();

                while (iter.hasNext()) {
                    // 取得上传文件
                    String fileName = iter.next();
                    MultipartFile file = multipartRequest.getFile(fileName);
                    if (!checkFileSize(file, model)) {
                        return model;
                    }
                    //源文件名
                    String originalFilename = file.getOriginalFilename();
                    //文件类型白名单
                    String whitelist_mime = (String) (request.getAttribute("whitelist_mime") != null ? request.getAttribute("whitelist_mime") : request.getParameter("whitelist_mime"));
                    if (StringUtils.isNotBlank(whitelist_mime) && !whitelist_mime.contains(file.getContentType())) {
                        model.put("msg", "请上传正确的文件类型：" + whitelist_mime);
                        break;
                    }

                    long time = new Date().getTime();
                    originalFilename = time + originalFilename.substring(originalFilename.lastIndexOf("."));

                    if (fileType.equals("video") && (file.getContentType().contains("video") || file.getContentType().contains("audio"))) {
                        File targetFile = new File(path, originalFilename);
                        if (!targetFile.exists()) targetFile.mkdirs();
                        file.transferTo(targetFile);
                        model.put("result", true);
                    } else if (fileType.equals("video") && (!file.getContentType().contains("video") || !file.getContentType().contains("audio"))) {
                        model.put("msg", "不是一个有效的视频文件");
                        return model;
                    } else {
                        model.put("msg", "非法文件");
                        break;
                    }
                    if ((boolean) model.get("result")) {
                        model.put("fileUrl", relativeFilePath + originalFilename);
                    }

                    break;
                }
            } else {
                model.put("msg", "please chose  a file！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (iis != null) {
                try {
                    iis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return model;
    }

    /**
     * 创建保存目录
     * 
     * @param folderName
     * @param model
     * @return
     */
    public String createFilePath(HttpServletRequest request, String relativeFilePath) {
        String imagewebroot = PropertiesConfig.readValue("imagewebroot");
        if (StringUtils.isBlank(imagewebroot)) {
            imagewebroot = request.getServletContext().getRealPath("/");
        }
        FileUtil.createDirectroy(imagewebroot + relativeFilePath);
        return imagewebroot + relativeFilePath;
    }

    /**
     * 获取相对路径
     * 
     * @param folderName
     * @param model
     * @return
     */
    public String getRelativeFilePath(String folderName, String type) {
        if (StringUtils.isBlank(type)) {
            type = "images";
        }
        return "/upload/" + type + "/" + folderName + Calendar.getInstance().get(Calendar.YEAR) + "/" + (Calendar.getInstance().get(Calendar.MONTH) + 1) + "/"
                + Calendar.getInstance().get(Calendar.DAY_OF_MONTH) + "/";
    }

    /**
     * 校验文件大小
     * 
     * @param file
     * @param model
     * @return
     */
    public boolean checkFileSize(MultipartFile file, Map<String, Object> model) {
        String imageMaxSizeStr = PropertiesConfig.readValue("imageMaxSize");
        int imageMaxSize = 1;
        if (StringUtils.isNotBlank(imageMaxSizeStr)) {
            imageMaxSize = Integer.valueOf(imageMaxSizeStr);
        }
        //获得文件信息
        if (file == null || file.getSize() == 0) {
            model.put("msg", "please chose  a file！");
            return true;
        } else if (file.getSize() > imageMaxSize * 1024 * 1024) {
            //上传图片文件大小限制在1M内
            model.put("msg", "图片文件不能超过" + imageMaxSize + "M！");
            return true;
        }

        return true;
    }

    /**
     * 上传文件(默认图片文件)
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/fileUpload")
    @ResponseBody
    public Map<String, Object> upload(HttpServletRequest request) {
        Map<String, Object> model = new HashMap<String, Object>();
        FileInputStream fis = null;
        model.put("result", false);
        model.put("msg", "上传失败");
        try {
            // 转型为MultipartHttpRequest：
            String folderName = "";
            folderName = request.getParameter("folderName");
            String fileType = request.getParameter("type") == null ? "img" : request.getParameter("type");
            if (StringUtils.isNotBlank(folderName)) {
                folderName = folderName + "/";
            } else {
                folderName = "";

            }
            // 创建一个通用的多部分解析器
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
            // 判断 request 是否有文件上传,即多部分请求
            if (multipartResolver.isMultipart(request)) {
                MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
                String relativeFilePath = getRelativeFilePath(folderName, "file");
                String path = createFilePath(request, relativeFilePath);
                Iterator<String> iter = multipartRequest.getFileNames();

                while (iter.hasNext()) {
                    // 取得上传文件
                    String file = iter.next();
                    MultipartFile mulfile = multipartRequest.getFile(file);
                    if (!checkFileSize(mulfile, model)) {
                        return model;
                    }
                    // 获得文件：
                    if (file == null || mulfile.getSize() == 0) {
                        model.put("msg", "please chose  a file！");
                    } else {
                        if (file != null) {
                            String fileName = mulfile.getOriginalFilename();
                            String whitelist_mime = (String) (request.getAttribute("whitelist_mime") != null ? request.getAttribute("whitelist_mime") : request.getParameter("whitelist_mime"));// 文件类型白名单
                            if (StringUtils.isNotBlank(whitelist_mime) && !whitelist_mime.contains(mulfile.getContentType())) {
                                model.put("msg", "请上传正确的文件类型：" + whitelist_mime);
                                return model;
                            }
                            long time = new Date().getTime();
                            fileName = time + fileName.substring(fileName.lastIndexOf("."));
                            if (fileType.equals("img")) {
                                disposeImg(request, mulfile, model, fileName, path);
                            } else {
                                File targetFile = new File(path, fileName);
                                if (!targetFile.exists()) targetFile.mkdirs();
                                mulfile.transferTo(targetFile);
                                model.put("result", true);
                                model.put("msg", "上传成功");
                            }
                            if ((boolean) model.get("result")) {
                                model.put("fileUrl", relativeFilePath + fileName);
                            }
                        }
                    }
                }

            } else {
                model.put("msg", "please chose  a file！");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return model;
    }

    /**
     * 上传BASE64编码后的文件字符串
     * 
     * @param request
     * @return
     */
    @RequestMapping(value = "/fileStrUpload")
    @ResponseBody
    public Map<String, Object> fileStrUpload(HttpServletRequest request) {
        Map<String, Object> model = new HashMap<String, Object>();
        BufferedOutputStream stream = null;
        model.put("result", false);
        model.put("msg", "上传失败");
        try {
            String folderName = "";
            folderName = request.getParameter("folderName");
            String fileType = request.getParameter("type") == null ? "file" : request.getParameter("type");
            if (StringUtils.isNotBlank(folderName)) {
                folderName = folderName + "/";
            } else {
                folderName = "";
            }

            String suffix = request.getParameter("suffix");
            if (StringUtils.isBlank(suffix)) {
                model.put("msg", "缺少文件类型参数：suffix");
                return model;
            }
            String fileStr = request.getParameter("fileStr");
            if (fileStr == null) {
                model.put("msg", "缺少文件BASE64字符串：fileStr");
                return model;
            }
            fileStr = URLDecoder.decode(fileStr,"UTF-8");
            fileStr = fileStr.replaceAll(" ", "+");
            String imageMaxSizeStr = PropertiesConfig.readValue("imageMaxSize");
            int imageMaxSize = 1;
            if (StringUtils.isNotBlank(imageMaxSizeStr)) {
                imageMaxSize = Integer.valueOf(imageMaxSizeStr);
            }
            if (fileStr.length() > imageMaxSize * 1024 * 1024) {
                //上传图片文件大小限制在1M内
                model.put("msg", "图片文件不能超过" + imageMaxSize + "M！");
                return model;
            }
            String relativeFilePath = getRelativeFilePath(folderName, fileType);
            String path = createFilePath(request, relativeFilePath);
            String fileName = new Date().getTime() + "." + suffix;
            //Base64解码
            byte[] b = Base64.decodeBase64(fileStr);
            for (int i = 0; i < b.length; ++i) {
                if (b[i] < 0) {//调整异常数据
                    b[i] += 256;
                }
            }
            //生成jpeg图片
            stream = new BufferedOutputStream(new FileOutputStream(path + fileName));
            stream.write(b);
            model.put("result", true);
            model.put("msg", "上传成功");
            model.put("fileUrl", relativeFilePath + fileName);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return model;
    }

    /**
     * 处理图片
     * @author n004881
     * 
     * @param request
     * @param multipartFile
     * @param model
     * @param fileName
     * @param path 图片绝对路径
     * 
     */
    private void disposeImg(HttpServletRequest request, MultipartFile multipartFile, Map<String, Object> model, String fileName, String path) {
        BufferedImage sourceImg = null;
        try {
            sourceImg = ImageIO.read(multipartFile.getInputStream());
            // 图片长宽校验
            String whitelist_norms = request.getParameter("whitelist_norms");// 文件大小规格白名单
            String norms = sourceImg.getWidth() + "*" + sourceImg.getHeight();
            if (StringUtils.isNotBlank(whitelist_norms) && !whitelist_norms.contains(norms)) {
                model.put("msg", "请上传正确的文件规格：" + whitelist_norms);
                return;
            }

            File targetFile = new File(path, fileName);
            if (!targetFile.exists()) targetFile.mkdirs();
            // 保存图片
            multipartFile.transferTo(targetFile);
            String issy = request.getParameter("nosy");// 是否添加水印
            if (StringUtils.isNotBlank(issy) && issy.equals("1")) {// 不添加
                String sysrc = request.getSession().getServletContext().getRealPath("static/image/sy.png");
                ImageUtils.pressImage(sysrc, path + "/" + fileName, path + "/" + fileName, 0, 0.6f);
            }

            // 是否切图
            String isqt = request.getParameter("isqt");

            if (StringUtils.isNotBlank(isqt)) {
                sourceImg = ImageIO.read(targetFile);

                String cutName = path + "/" + fileName + "-120.jpg";
                if (sourceImg.getWidth() > 120) {
                    // 裁剪120宽度图片
                    ImgCutUtil.zoomImage(path + "/" + fileName, cutName, 120, (int) (sourceImg.getHeight() / (sourceImg.getWidth() / 120.0)));
                } else {
                    targetFile = new File(cutName);
                    if (!targetFile.exists()) targetFile.mkdirs();
                    ImageIO.write(sourceImg, cutName.substring(cutName.lastIndexOf(".") + 1), targetFile);
                }
                cutName = path + "/" + fileName + "-60.jpg";
                if (sourceImg.getWidth() > 60) {
                    // 裁剪60宽度图片
                    ImgCutUtil.zoomImage(path + "/" + fileName, cutName, 60, (int) (sourceImg.getHeight() / (sourceImg.getWidth() / 60.0)));
                } else {
                    targetFile = new File(cutName);
                    if (!targetFile.exists()) targetFile.mkdirs();
                    ImageIO.write(sourceImg, cutName.substring(cutName.lastIndexOf(".") + 1), targetFile);
                }
            }

            model.put("result", true);
            model.put("msg", "上传成功");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (sourceImg != null) {
                sourceImg.flush();
            }
        }
    }

    /**
     * 上传多个文件
     * 
     * @param request
     * @param model
     * @return
     * 
     * @author n004881
     */
    @RequestMapping(value = "/fileUploads")
    @ResponseBody
    public Map<String, Object> uploadFiles(HttpServletRequest request) {
        Map<String, Object> model = new HashMap<String, Object>();
        File targetFile = null;
        FileInputStream fis = null;
        try {
            model.put("result", "ERROR");
            // 转型为MultipartHttpRequest：
            String folderName = "";
            folderName = request.getParameter("folderName");
            if (StringUtils.isNotBlank(folderName))
                folderName = folderName + "/";
            else
                folderName = "";

            // 创建一个通用的多部分解析器
            CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
            // 判断 request 是否有文件上传,即多部分请求
            if (multipartResolver.isMultipart(request)) {
                // 转换成多部分request
                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
                // 取得request中的所有文件名
                Iterator<String> iter = multiRequest.getFileNames();
                String relativeFilePath = getRelativeFilePath(folderName, "file");
                String path = createFilePath(request, relativeFilePath);
                while (iter.hasNext()) {
                    // 取得上传文件
                    MultipartFile file = multiRequest.getFile(iter.next());
                    if (file != null) {
                        // 取得当前上传文件的文件名称
                        String myFileName = file.getOriginalFilename();
                        StringBuffer fileUrls = new StringBuffer();
                        // 如果名称不为“”,说明该文件存在，否则说明该文件不存在
                        if (StringUtils.isNotBlank(myFileName.trim())) {
                            String fileName = file.getOriginalFilename();
                            long time = new Date().getTime();
                            fileName = time + fileName.substring(fileName.lastIndexOf("."));
                            targetFile = new File(path, fileName);
                            if (!targetFile.exists()) targetFile.mkdirs();
                            // 保存
                            file.transferTo(targetFile);
                            fileUrls.append(relativeFilePath + fileName + ",");
                        }
                        model.put("fileUrl", fileUrls);
                        model.put("result", "SUCCESS");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return model;
    }

}