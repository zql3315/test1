<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"></c:set>
<!DOCTYPE html>
<html lang="en">
<head>
<title>My JSP "index.jsp" starting page</title>

<script src="${ctx }/static/ace/assets/js/flot/jquery.flot.min.js"></script>
<script src="${ctx }/static/ace/assets/js/flot/jquery.flot.pie.min.js"></script>
<script src="${ctx}/static/ace/assets/js/Chart.min.js"></script>
<%--下面的js会影响其他页面的select组件的效果--%>
<%--<script src="${ctx }/static/ace/assets/js/flot/jquery.flot.resize.min.js"></script>--%>

</head>

<body>
	<div class="row">
		<div class="col-sm-6">
			<div class="widget-box">
				<div class="widget-header widget-header-flat widget-header-small">
					<h5 class="widget-title">
						<i class="ace-icon fa fa-signal"></i> 来源统计
					</h5>
					<div class="widget-toolbar no-border">
						<div class="inline dropdown-hover">
							<button class="btn btn-minier btn-primary">
								这周 <i class="ace-icon fa fa-angle-down icon-on-right bigger-110"></i>
							</button>
							<ul class="dropdown-menu dropdown-menu-right dropdown-125 dropdown-lighter dropdown-close dropdown-caret">
								<li class="active"><a class="blue" href="#"> <i class="ace-icon fa fa-caret-right bigger-110">&nbsp;</i> 这周 </a></li>
								<li><a href="#"> <i class="ace-icon fa fa-caret-right bigger-110 invisible">&nbsp;</i> 上周 </a></li>
								<li><a href="#"> <i class="ace-icon fa fa-caret-right bigger-110 invisible">&nbsp;</i> 当月 </a></li>
								<li><a href="#"> <i class="ace-icon fa fa-caret-right bigger-110 invisible">&nbsp;</i> 下月 </a></li>
							</ul>
						</div>
					</div>
				</div>
				<div class="widget-main">
					<div id="piechart-placeholder"></div>
				</div>
			</div>
		</div>
		<div class="col-sm-6">
			<div class="widget-box">
				<div class="widget-header widget-header-flat">
					<h4 class="widget-title lighter">
						<i class="ace-icon fa fa-signal"></i> 销售统计
					</h4>
					<div class="widget-toolbar">
						<a data-action="collapse" href="#"> <i class="ace-icon fa fa-chevron-up"></i> </a>
					</div>
				</div>
				<div class="widget-body">
					<div class="widget-body-inner">
						<div class="widget-main padding-4">
							<div id="sales-charts"></div>
						</div>
					</div>
				</div>
			</div>
		</div>
		<!-- 柱状图 -->
		<div class="col-sm-6">
		    <div class="span6">
				<div class="widget-box">
					<div class="widget-title">
						<span class="icon">
							<i class="icon-signal"></i>
						</span>
						<!-- <h3>票数统计</h3> -->
					</div>
					<div class="widget-content">
						<div class="bars" style="padding: 0px; position: relative;">
						    <canvas id="myChart" width="400" height="411"></canvas>
						    <input type="hidden" id="datas" value="${datas }">
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
<script type="text/javascript">
	var placeholder = $("#piechart-placeholder").css({
		"width" : "50%",
		"min-height" : "150px"
	});
	var data = [ {
		label : "浏览器",
		data : 38.7,
		color : "#68BC31"
	}, {
		label : "手机端",
		data : 24.5,
		color : "#2091CF"
	}, {
		label : "平板",
		data : 8.2,
		color : "#AF4E96"
	}, {
		label : "电视",
		data : 18.6,
		color : "#DA5430"
	}, {
		label : "other",
		data : 10,
		color : "#FEE074"
	} ]
	function drawPieChart(placeholder, data, position) {
		$.plot(placeholder, data, {
			series : {
				pie : {
					show : true,
					tilt : 0.8,
					highlight : {
						opacity : 0.25
					},
					stroke : {
						color : "#fff",
						width : 2
					},
					startAngle : 2
				}
			},
			legend : {
				show : true,
				position : position || "ne",
				labelBoxBorderColor : null,
				margin : [ -30, 15 ]
			},
			grid : {
				hoverable : true,
				clickable : true
			}
		})
	}
	drawPieChart(placeholder, data);
	placeholder.data("chart", data);
	placeholder.data("draw", drawPieChart);
	//pie chart tooltip example
	var $tooltip = $(
			"<div class=\"tooltip top in\"><div class=\"tooltip-inner\"></div></div>")
			.hide().appendTo("body");
	var previousPoint = null;

	placeholder.on("plothover", function(event, pos, item) {
		if (item) {
			if (previousPoint != item.seriesIndex) {
				previousPoint = item.seriesIndex;
				var tip = item.series["label"] + " : " + item.series["percent"]
						+ "%";
				$tooltip.show().children(0).text(tip);
			}
			$tooltip.css({
				top : pos.pageY + 10,
				left : pos.pageX + 10
			});
		} else {
			$tooltip.hide();
			previousPoint = null;
		}

	});

	//线状图
	var d1 = [];
	for ( var i = 0; i < Math.PI * 2; i += 0.5) {
		d1.push([ i, Math.sin(i) ]);
	}

	var d2 = [];
	for ( var i = 0; i < Math.PI * 2; i += 0.5) {
		d2.push([ i, Math.cos(i) ]);
	}

	var d3 = [];
	for ( var i = 0; i < Math.PI * 2; i += 0.2) {
		d3.push([ i, Math.tan(i) ]);
	}
	var sales_charts = $("#sales-charts").css({
		"width" : "100%",
		"min-height" : "220px"
	});
	$.plot("#sales-charts", [ {
		label : "Domains",
		data : d1
	}, {
		label : "Hosting",
		data : d2
	}, {
		label : "Services",
		data : d3
	} ], {
		hoverable : true,
		shadowSize : 0,
		series : {
			lines : {
				show : true
			},
			points : {
				show : true
			}
		},
		xaxis : {
			tickLength : 0
		},
		yaxis : {
			ticks : 10,
			min : -2,
			max : 2,
			tickDecimals : 3
		},
		grid : {
			backgroundColor : {
				colors : [ "#fff", "#fff" ]
			},
			borderWidth : 1,
			borderColor : "#555"
		}
	});
	
	//柱状图
	var labels = ["No.001", "No.002", "No.003", "No.004", "No.005", "No.006", "No.007","No.008","No.009","No.010",
	              "No.011", "No.012", "No.013", "No.014", "No.015", "No.016", "No.017","No.018","No.019","No.020",
	              "No.021","No.022","No.023","No.024","No.025"];
	              
	var datas = $("#datas").val();
	datas = eval(datas);
	var data = {
    labels: labels,
    datasets: [
	        {
	            label: "票数",
	            backgroundColor: "rgba(255,99,132,0.2)",
	            borderColor: "rgba(255,99,132,1)",
	            borderWidth: 1,
	            hoverBackgroundColor: "rgba(255,99,132,0.4)",
	            hoverBorderColor: "rgba(255,99,132,1)",
	            data: [650, 590, 800, 810, 560, 550, 400,600,650, 1009, 500, 81, 560, 550, 400,600,810, 560, 550, 400,600,220,563,111,259,0],
	        }
	    ]
	};

    var ctx = document.getElementById("myChart").getContext("2d");
	var myBarChart = new Chart(ctx, {
	    type: 'bar',
	    data: data,
	    options: 'bottom'
	});
</script>
