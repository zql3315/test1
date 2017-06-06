<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>注册人员分布</title>
        <script type="text/javascript" src="${ctx }/static/map/js/lib/raphael-min.js"></script>
        <script type="text/javascript" src="${ctx }/static/map/js/chinaMapConfig.js"></script>
        <script type="text/javascript" src="${ctx }/static/map/js/worldMapConfig.js"></script>
        <script type="text/javascript" src="${ctx }/static/map/js/map.js"></script>

        <style type="text/css">
            .wrap ol, ul, li {
			    list-style: outside none none;
			}
            /* 提示自定义 */
            .stateTip, #StateTip{display:none; position:absolute; padding:8px; background:#fff; border:2px solid #2385B1; -moz-border-radius: 4px; -webkit-border-radius: 4px; border-radius: 4px; font-size:12px; font-family:Tahoma; color:#333;}
            .wrap{
                margin: 0 auto;
            }
            .wrap h2{ margin:0px; padding-left:10px; background-color: #ddd; font-size:14px; line-height:30px;}
            .wrap .items{
                border: 1px solid #ddd;
                float: left;
            }
            .regionList{float:left; margin-top:15px;}
            .regionList ul{float:left; width:120px; height:100%; margin-right:5px; display:inline; font-family:"微软雅黑"}
            .regionList ul li{ height:24px; padding:0 0px; border:1px solid #fff; line-height:24px;}
            .regionList ul li.select{border:1px solid #D3D3D3; background:#FFF1BF}

            .mapInfo i{ display:inline-block; width:15px; height:15px; margin-top:5px; line-height:15px; font-style:normal; background:#aeaeae; color:#fff; font-size:11px; font-family: Tahoma; -webkit-border-radius:15px; border-radius:15px; text-align:center}
            .mapInfo i.active{background:#E27F21;}
            .mapInfo span{ padding:0 5px 0 3px;}
            .mapInfo b{ font-weight:normal; color:#2770B5}

            .regionMap{float:left; margin-left:70px; display:inline;}
        </style>

        <script type="text/javascript">
            $(function() {
            	
            	var data = {
                        "heilongjiang": {"stateInitColor": 1, "baifenbi": 0.236},
                        "beijing": {"stateInitColor": 2},
                        "shanghai": {"stateInitColor": 3},
                        "tianjin": {"stateInitColor": 4},
                        "sichuan": {"stateInitColor": 5},
                        "shandong": {"stateInitColor": 6},
                        "shanxi": {"stateInitColor": 3},
                        "zhejiang": {"stateInitColor": 4},
                        "jiangshu": {"stateInitColor": 2},
                        "hunan": {"stateInitColor": 4},
                        "guizou": {"stateInitColor": 5},
                        "neimenggu": {"stateInitColor": 6},
                        "henan": {"stateInitColor": 3},
                        "gansu": {"stateInitColor": 4},
                        "ningxia": {"stateInitColor": 2},
                        "jilin": {"diabled": true}
                };
            	//地图1
            	$("#ChinaMap").SVGMap({
                    mapName: "china",
                    //mapWidth: 700,
                    //mapHeight: 600,
                    stateData: data,
                    stateTipHtml: function(stateData, obj){
                        return 'id:' + ((stateData)[obj.id] && (stateData)[obj.id].baifenbi || obj.id) + '<br>name:' + obj.name; 
                    },
                    hoverCallback: function(stateData, obj){//hover回调
                        //alert('hover:'+obj.name);
                    },
                    clickCallback: function(stateData, obj){//click回调
                        alert("点击了："+obj.name);
                    }
                });

            	
            	//地图2
                // $.ajax({
                //  url: projectName+'/idea123Action.do?method=getIdea123MapData&reportName='+reportName,
                //  data: data,
                //  dataType: 'json',
                //  success: function(data){

                var data = {
	                		"jiangsu": {"value": "30.05%", "index": "1", "stateInitColor": "0"}, 
	                		"henan": {"value": "19.77%", "index": "2", "stateInitColor": "0"}, 
	                		"anhui": {"value": "10.85%", "index": "3", "stateInitColor": "0"}, 
	                		"zhejiang": {"value": "10.02%", "index": "4", "stateInitColor": "0"}, 
	                		"liaoning": {"value": "8.46%", "index": "5", "stateInitColor": "0"}, 
	                		"beijing": {"value": "4.04%", "index": "6", "stateInitColor": "1"}, 
	                		"hubei": {"value": "3.66%", "index": "7", "stateInitColor": "1"}, 
	                		"jilin": {"value": "2.56%", "index": "8", "stateInitColor": "1"}, 
	                		"shanghai": {"value": "2.47%", "index": "9", "stateInitColor": "1"}, 
	                		"guangxi": {"value": "2.3%", "index": "10", "stateInitColor": "1"}, 
	                		"sichuan": {"value": "1.48%", "index": "11", "stateInitColor": "2"}, 
	                		"guizhou": {"value": "0.99%", "index": "12", "stateInitColor": "2"}, 
	                		"hunan": {"value": "0.78%", "index": "13", "stateInitColor": "2"}, 
	                		"shandong": {"value": "0.7%", "index": "14", "stateInitColor": "2"}, 
	                		"guangdong": {"value": "0.44%", "index": "15", "stateInitColor": "2"}, 
	                		"jiangxi": {"value": "0.34%", "index": "16", "stateInitColor": "3"}, 
	                		"fujian": {"value": "0.27%", "index": "17", "stateInitColor": "3"}, 
	                		"yunnan": {"value": "0.23%", "index": "18", "stateInitColor": "3"}, 
	                		"hainan": {"value": "0.21%", "index": "19", "stateInitColor": "3"}, 
	                		"shanxi": {"value": "0.11%", "index": "20", "stateInitColor": "3"}, 
	                		"hebei": {"value": "0.11%", "index": "21", "stateInitColor": "4"}, 
	                		"neimongol": {"value": "0.04%", "index": "22", "stateInitColor": "4"}, 
	                		"tianjin": {"value": "0.04%", "index": "23", "stateInitColor": "4"}, 
	                		"gansu": {"value": "0.04%", "index": "24", "stateInitColor": "4"}, 
	                		"shaanxi": {"value": "0.02%", "index": "25", "stateInitColor": "4"}, 
	                		"macau": {"value": "0.0%", "index": "26", "stateInitColor": "7"}, 
	                		"hongkong": {"value": "0.0%", "index": "27", "stateInitColor": "7"}, 
	                		"taiwan": {"value": "0.0%", "index": "28", "stateInitColor": "7"}, 
	                		"qinghai": {"value": "0.0%", "index": "29", "stateInitColor": "7"}, 
	                		"xizang": {"value": "0.0%", "index": "30", "stateInitColor": "7"}, 
	                		"ningxia": {"value": "0.0%", "index": "31", "stateInitColor": "7"}, 
	                		"xinjiang": {"value": "0.0%", "index": "32", "stateInitColor": "7"}, 
	                		"heilongjiang": {"value": "0.0%", "index": "33", "stateInitColor": "7"}, 
	                		"chongqing": {"value": "0.0%", "index": "34", "stateInitColor": "7"}
                		};
                var i = 1;
                for (k in data) {
                    if (i <= 12) {
                        var _cls = i < 4 ? "active" : "";
                        $('#MapControl .list1').append('<li name="' + k + '"><div class="mapInfo"><i class="' + _cls + '">' + (i++) + '</i><span>' + chinaMapConfig.names[k] + '</span><b>' + data[k].value + '</b></div></li>')
                    } else if (i <= 24) {
                        $('#MapControl .list2').append('<li name="' + k + '"><div class="mapInfo"><i>' + (i++) + '</i><span>' + chinaMapConfig.names[k] + '</span><b>' + data[k].value + '</b></div></li>')
                    } else {
                        $('#MapControl .list3').append('<li name="' + k + '"><div class="mapInfo"><i>' + (i++) + '</i><span>' + chinaMapConfig.names[k] + '</span><b>' + data[k].value + '</b></div></li>')
                    }
                }

                var mapObj_1 = {};
                var stateColorList = ["003399", "0058B0", "0071E1", "1C8DFF", "51A8FF", "82C0FF", "AAD5ee", "AAD5FF"];

                $("#RegionMap").SVGMap({
                    external: mapObj_1,
                    mapName: "china",
                    //mapWidth: 350,
                    //mapHeight: 350,
                    stateData: data,
                    // stateTipWidth: 118,
                    // stateTipHeight: 47,
                    // stateTipX: 2,
                    // stateTipY: 0,
                    stateTipHtml: function(mapData, obj) {
                        var _value = mapData[obj.id].value;
                        var _idx = mapData[obj.id].index;
                        var active = "";
                        _idx < 4 ? active = "active" : active = "";
                        var tipStr = '<div class="mapInfo"><i class="' + active + '">' + _idx + '</i><span>' + obj.name + '</span><b>' + _value + '</b></div>';
                        return tipStr;
                    }
                });
                $("#MapControl li").hover(function() {
                    var thisName = $(this).attr("name");

                    var thisHtml = $(this).html();
                    $("#MapControl li").removeClass("select");
                    $(this).addClass("select");
                    $(document.body).append('<div id="StateTip"></div');
                    $("#StateTip").css({
                        left: $(mapObj_1[thisName].node).offset().left - 50,
                        top: $(mapObj_1[thisName].node).offset().top - 40
                    }).html(thisHtml).show();
                    mapObj_1[thisName].attr({
                        fill: "#E99A4D"
                    });
                }, function() {
                    var thisName = $(this).attr("name");

                    $("#StateTip").remove();
                    $("#MapControl li").removeClass("select");
                    mapObj_1[$(this).attr("name")].attr({
                        fill: "#" + stateColorList[data[$(this).attr("name")].stateInitColor]
                    });
                });

                $("#MapColor").show();
                //  }
                // });

                
                
                //地图3：世界地图
                $('#WorldMap').SVGMap({
                    mapName: 'world',
                    mapWidth: 600,
                    mapHeight: 400
                });
                
            });
        </script>
    </head>
    <body>
        <div class="row">
	        <div class="col-xs-12">
	            <h3 class="header smaller lighter blue">
	                <i class="ace-icon fa fa-pencil-square-o"></i>
	                                               人员分布
	            </h3>
	        </div>
	        <div class="col-xs-12">
                <div class="row wrap">
                  <div class="col-sm-12">
                    <div class="wrap">
                        <div class="items">
	                        <h2>注册人员分布1</h2>
                            <a href="javascript:;" class="fold"></a>
                            <div class="itemCon" align="center">
                                <div id="ChinaMap" align="center"></div>
                            </div>
                        </div>
                    </div>
                  </div>
                </div>
                <div class="space"></div>
		        <div class="row wrap">
		          <div class="col-sm-12">
		            <div class="items">
			            <h2>注册人员分布2</h2>
		                <a href="javascript:;" class="fold"></a>
		                <div class="itemCon">
		                    <div id="Region" style="position:relative; ">
		                        <div class="regionList" id="MapControl">
		                            <ul class="list1"></ul>
		                            <ul class="list2"></ul>
		                            <ul class="list3"></ul>
		                        </div>
		                        <div class="regionMap" id="RegionMap"></div>
		                        <div id="MapColor" style=" display:none; float:left; width:30px; height:180px; margin:80px 0 0 10px; display:inline; background:url(${ctx}/static/map/images/map_color.png) center 0;"></div>
		                    </div>
		
		                </div>
		            </div>
		          </div>
		        </div>
		        <div class="space"></div>
		        <div class="row wrap">
                  <div class="col-sm-12">
                    <div class="wrap">
                        <div class="items">
                            <h2>世界地图</h2>
			                <a href="javascript:;" class="fold"></a>
			                <div class="itemCon">
			                    <div id="WorldMap"></div>
			                </div>
                        </div>
                    </div>
                  </div>
                </div>
	        </div>
        </div>
       
    </body>
</html>