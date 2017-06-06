<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>

<style>
  .gauge {
    width: 350px;
    height: 350px;
    float: left;
  }

  a:link.button,
  a:active.button,
  a:visited.button,
  a:hover.button {
    margin: 30px 5px 0 2px;
    padding: 7px 13px;
  }
</style>
<div class="container">
   <div id="gg1" class="gauge"></div>
   <div id="gg2" class="gauge"></div>
   <div id="gg3" class="gauge"></div>
 </div>
 
  <script src="${ctx}/static/justgage-1.2.2/raphael-2.1.4.min.js"></script>
  <script src="${ctx}/static/justgage-1.2.2/justgage.js"></script>
  <script>
  !function(event) {
	var currentVal = ${totalMemory }-${freeMemory }
    var gg1 = new JustGage({
      id: "gg1",
      value : currentVal,
      min: 0,
      max: ${maxMemory },
      decimals: 2,
<%--      symbol: '%',--%>
      pointer: true,
      gaugeWidthScale: 0.6,
      customSectors: [{
        color : "#00ff00",
        lo : 0,
        hi : ${maxMemory }*0.40
      },{
    	  color : "#DD6D22",
          lo : ${maxMemory }*0.40,
          hi : ${maxMemory }*0.80
      },{
        color : "#ff0000",
        lo : ${maxMemory }*0.80,
        hi : ${maxMemory }
      }],
      title: "虚拟机内存",
      label: "已使用内存(MB)",
<%--      textRenderer: customValue,--%>
      counter: true
    });
    var gg2 = new JustGage({
      id: "gg2",
      value : ${totalMemory },
      min: 0,
      max: ${maxMemory },
      decimals: 2,
      gaugeWidthScale: 0.4,
      pointer: true,
      pointerOptions: {
        toplength: -15,
        bottomlength: 10,
        bottomwidth: 12,
        color: '#8e8e93',
        stroke: '#ffffff',
        stroke_width: 3,
        stroke_linecap: 'round'
      },
      customSectors: [{
        color : "#00ff00",
        lo : 0,
        hi : ${maxMemory }*0.40
      },{
    	  color : "#DD6D22",
          lo : ${maxMemory }*0.40,
          hi : ${maxMemory }*0.80
      },{
        color : "#ff0000",
        lo : ${maxMemory }*0.80,
        hi : ${maxMemory }
      }],
      title: "虚拟机内存",
      label: "已获取内存(MB)",
      counter: true
    });
    var gg3 = new JustGage({
      id: "gg3",
      value : ${freeMemory },
      min: 0,
      max: ${maxMemory },
      decimals: 2,
      pointer: true,
      gaugeWidthScale: 0.1,
      pointerOptions: {
          toplength: 8,
          bottomlength: -20,
          bottomwidth: 6,
          color: '#8e8e93'
        },
      customSectors: [{
        color : "#00ff00",
        lo : 0,
        hi : ${maxMemory }*0.40
      },{
    	  color : "#DD6D22",
          lo : ${maxMemory }*0.40,
          hi : ${maxMemory }*0.80
      },{
        color : "#ff0000",
        lo : ${maxMemory }*0.80,
        hi : ${maxMemory }
      }],
      title: "虚拟机内存",
      label: "空闲内存(MB)",
      counter: true
    });

    function customValue(val) {
        if (val < ${maxMemory }*0.20) {
            return '极好';
        } else if (val > ${maxMemory }*0.20 && val < ${maxMemory }*0.50) {
            return '良好';
        } else if (val > ${maxMemory }*0.50 && val < ${maxMemory }*0.80) {
            return '稳定';
        } else if (val > ${maxMemory }*0.80 && val < ${maxMemory }*0.90) {
            return '警告';
        } else if (val > ${maxMemory }*0.9) {
            return '危险';
        }
    }

  }();
  </script>
