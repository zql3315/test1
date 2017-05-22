$(function() {
	var whitelist_ext = [ "jpeg", "jpg", "png", "gif", "bmp" ];
	var whitelist_mime = [ "image/jpg", "image/jpeg", "image/png", "image/gif",
			"image/bmp" ];
	$(".picfile").ace_file_input("update_settings", {
		"btn_choose" : "选择文件",
		"btn_change" : "重新选择",
		"no_file" : "请选择一个LOGO图片",
		//maxSize: 100000, // ~100 KB
		"allowExt" : whitelist_ext,
		"allowMime" : whitelist_mime
	});
	$(".picfile").show();
	$(".picfile").ace_file_input("reset_input");
})