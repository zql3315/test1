/**
 * 自定义菜单页面js
 * @author 003598
 * @date 20131025
 */
var personal_menu = {
	/** 初始化页面接口 */
	init : function() {
		this.initPage.init();
		this.btn.init();
	},
	
	
	
	initPage : {
		init : function() {
			personal_menu.opSort($('.menubody ul'));
			personal_menu.opSort($('.subList'));
			this.sortStop();
			this.getPreMenuData();
			this.initSelectParam();
		},
		
		//初始化页面时，通过判断一级菜单的状态，来控制其子菜单是否可选
		initSelectParam:function() {
			//定位到一级菜单状态下拉框处
			$('.parentList>dl>select[name=status]').each(function(i,v){
				var _this = $(v);
				
				//获得一级菜单下拉框状态的值
				var val = _this.val();
				
				//若一级菜单为停用状态
				if (val == 0)
				{
					// 子菜单状态修改（通过一级菜单状态下拉框对象找到对应在菜单中的状态下拉框对象）
					_this.parents('.preMenuLi').find('.subArray .status').each(function(i, v) {
						var _this = $(v);
						_this.attr("disabled", "disabled");
					});
					
				}
				else if (val == 1)//若一级菜单为启用状态，则将disabled属性移除
				{
					_this.removeAttr('disabled');
				}
			});
		},
		
		
		
		sortStop: function() {
			$('.menubody').delegate('.subList','sortstop', function(event, ui) {
				var preMenuLis = $('.preMenuLi');
				//变量所有父菜单节点
				$.each(preMenuLis, function(i, p){
					//遍历父菜单下所有节点 找到存储子菜单的节点
					$.each(p.childNodes, function(j, subArray){
						if(subArray.className.indexOf('subArray')!=-1){
							//遍历子菜单容器获取所有子菜单
							$.each(subArray.childNodes, function(k, subList){
								$.each(subList.childNodes, function(l, subListC){
									if(subListC.tagName='DL'){
										$.each(subListC.childNodes, function(m, subListCC){
											if(subListCC.tagName='IMG'){
												if((subArray.childNodes.length-1)==k){
													subListCC.src = basePath+'/image/common/bg_repno_black.png';
												}else{
													subListCC.src = basePath+'/image/common/bg_repno.png';
												}
											}
										});
									}
								});
							});
						}
						
					});
				});
			});   
		},
		
		/**
		 * 获取自定义菜单信息
		 */
		getPreMenuData: function() {
			var action = "weiXinMenu/weiXinMenu_queryPersonalMenu.do";
			
			base.WAP_POST(action, null, "json", function(o){
				if (o){
					var preMenuList = o.perMenu;
					var $parentDiv = {};
					var $child = {};
					var $ul = $('.pMenu ul');
					$.each(preMenuList, function(i, v){
						if (v.parentid == 0){
							
							$parentDiv[v.menuid] = $(htmlTemp.parentDiv.format([v.menuname, v.menucontent]));
							var _thisSelect = $parentDiv[v.menuid].find('select');
							$(_thisSelect).find('.type_option_parent').each(function(){
								var $this = $(this);
								if ($this.val() === v.menutype){
									$this.attr("selected", true);
								}
							});
							$(_thisSelect).find('.status_option_parent').each(function(){
								var $this = $(this);
								if ($this.val() == v.status){
									$this.attr("selected", true);
								}
							});
							$ul.append($(htmlTemp.li).append($parentDiv[v.menuid]));
							
						}else {
							$child[v.menuid] = $(htmlTemp.chlidDiv.format([basePath, v.menuname, v.menucontent]));
							var _thisSelect = $child[v.menuid].find('select');
							$(_thisSelect).find('.type_option_chlid').each(function(){
								var $this = $(this);
								if ($this.val() === v.menutype){
									$this.attr("selected", true);
								}
							});
							$(_thisSelect).find('.status_option_chlid').each(function(){
								var $this = $(this);
								if ($this.val() == v.status){
									$this.attr("selected", true);
								}
							});
							if ($parentDiv[v.parentid]){
								$parentDiv[v.parentid].next('.subArray').append($child[v.menuid]);
							}
						}
						
					});
					
					personal_menu.opSort($('.subArray'));
					
					//  设置ul高度
					$ul.height(personal_menu.setUlHeight($ul));
				}
				
				// 设置结构图片
				$('.pMenu ul li').each(function() {
					var subListNum = $(this).find('.subList').size(); 
					$(this).find('.subList').each(function(i, v) {
						if ((i+1) < subListNum){
							$(this).find('img').attr('src', basePath + '/image/common/bg_repno.png' );
						}
						
					});
				});
				
			});
		}
	},
	
	/**
	 * 设置ul高度
	 */
	setUlHeight: function($ul) {
		var ul_height = 0;
		$ul.find('li').each(function() {
			ul_height += $(this).height();
		});
		
		return ul_height;
		
	},
	
	btn: {
		init: function(){
			this.addFMenuBtn();
			this.releaseBtn();
			this.saveBtn();
			this.addSubMenu();
			this.toDelete();
			this.qySelectBtn();
			
		},
		
		//当改变状态下拉框的值时执行
		qySelectBtn:function() {
			$('body').delegate('.parentList>dl>select[name=status]', 'change', function(){
				var firstVal = $(this).val();
				
				//获得对应子菜单的对象
				var $childSelectObj = $(this).parents('.preMenuLi').find('.subArray .status');
				
				//若一级菜单为停用状态
				if (firstVal === "0")
				{
					//置灰子菜单的状态选择框，并将值初始化为停用
					$childSelectObj.attr("disabled", "disabled");
					$childSelectObj.val('0');
				}
				else 
				{
					//变为可选状态
					$(this).parents('.preMenuLi').find('.subArray .status').removeAttr('disabled');
				}
			});
		},
	
		
		/** 保存按钮*/
		saveBtn: function(){
			$('#saveBtn').click(function() {
				var $menu = $('.menubody ul li .parentList,.subList');
				
				if ($menu.size() > 0){
					var a = "";
					var str = '[{0}]';
					var str1 = '{"menuname":"{0}","menutype":"{1}","menucontent":"{2}","parentid":"{3}","status":"{4}"}';
					var action = 'weiXinMenu/weiXinMenu_savePersonalMenu.do';
					$menu.each(function(_e, v){
						var $v = $(v);
						var titel = $v.find('.title').val();
						var type = $v.find('.level').val();
						var content = $v.find('.content').val();
						var parentId = $v.find('.parentId').val();
						var status = $v.find('.status').val();
						
						var fSize = _e+1;
						if (fSize>1){
							a +=",";
						}
						a += str1.format([titel,type,content,parentId,status]);
						
					});
					var perMenuList = str.format([a]);
					var data = {
							"perMenuList":perMenuList
					};
					base.WAP_POST(action, data, null, function(o) {
						if (10000 == o.statusCode)
						{
							alert('保存成功！');
						}
						else if(10007 == o.statusCode) 
						{
							alert('启用菜单个数越限，可以创建最多 3 个一级菜单，每个一级菜单下可以创建最多 5 个二级菜单，请重新编辑菜单！');
						}
						else
						{
							alert('保存失败！');
						}
					});
				}else {
					alert("请先编辑自定义菜单，再点击保存。");
				}
			});
		},
		
		/** 添加一级菜单*/
		addFMenuBtn: function(){
			$('.addFMenuBtn').click(function() {
            	var $ul = $('.pMenu ul');
            	// 拼装一级菜单
            	var $li = $(htmlTemp.li);
            	
            	var $parentDiv = $(htmlTemp.parentDiv.format(["",""]));
            	$li.append($parentDiv);
            	$ul.append($li);

				//  设置ul高度
	            var $ul = $('.pMenu ul');
				$ul.height(personal_menu.setUlHeight($ul));
				
			});
			
		},
		
		/** 发布按钮*/
		releaseBtn: function(){
			$('#send').click(function() {
				var action = "releasePersoanlMenuToWeixin.do";
				
				base.WAP_POST(action, null, null, function(o){
					if (10000 == o.statusCode)
					{
						alert('发布成功！');
					}
					else if(10007 == o.statusCode)
					{
						alert('启用菜单个数越限，可以创建最多 3 个一级菜单，每个一级菜单下可以创建最多 5 个二级菜单，请重新编辑菜单！');
					}
					else
					{
						alert('发布失败！');
					}
				});
			});
			
		},
		
		/**
		 * 二级菜单添加按钮
		 */
		addSubMenu:function(){
		    //添加二级菜单
	        $(".menubody").delegate(".addSubMenu","click",function () {
            	var $childDiv = $(htmlTemp.chlidDiv.format([basePath,"",""]));
            	var $preMenuLi = $(this).parents('.preMenuLi');
            	$prevImg =  $preMenuLi.find('.subList').last().find('img');
            	if ($prevImg.size()>0){
            		$prevImg.attr('src', basePath+'/image/common/bg_repno.png');
            	}
            	$preMenuLi.find('.subArray').append($childDiv);
            	
            	personal_menu.opSort($('.subArray'));
            	
            	//在添加二级菜单时，也需要做判断，停用：置灰
            	personal_menu.initPage.initSelectParam();
	            //  设置ul高度
	            var $ul = $('.pMenu ul');
				$ul.height(personal_menu.setUlHeight($ul));
	        });
		},
		
		toDelete:function(){
			$(".menubody").delegate(".toDelete","click",function () {
				
				var $thisDl_sub = $(this).parents('.subList');
				// 删除子菜单
				if ($thisDl_sub.size() > 0){
					
					var isDel = window.confirm("您确定删除该菜单?");
                    if (isDel) {
                    	
                    	// 判断当前元素是否是最后一个元素
    					if ($thisDl_sub.next().size() <= 0){
    						var $prevImg = $thisDl_sub.prev().find('img');
    						if ($prevImg.size()>0){
    							$prevImg.attr('src', basePath+'/image/common/bg_repno_black.png');
    						}
    					}
    					
                    	$thisDl_sub.remove();
                    }
					
				} else {
					
					// 删除父菜单
					var $thisDl_parent = $(this).parents('.parentList');
					var subListSzie = $thisDl_parent.parent().find('.subList').size();
					
					if (subListSzie > 0){
						
						alert("请先删除此菜单的所有二级菜单，再执行此操作");
	            		 return false;
					}
					var isDel = window.confirm("您确定删除该菜单?");
                    if (isDel) {
                    	$thisDl_parent.parent().remove();
                    }
				}
				
				//  设置ul高度
	            var $ul = $('.pMenu ul');
				$ul.height(personal_menu.setUlHeight($ul));
				
			});
		}
	},
	
	// 排序事件
	opSort : function(_$){
		if(_$.sortable('option','disabled')){
			_$.sortable({
				
			});
			
		}
	},
		
};

var personMenuObj = function() {
	var value = function() {
		this.parentDiv = '';
		this.childDiv = '';
	};
	
	var values = {};
	
	var add = function(){
		var args = arguments[0];
        var val = new value();
        for (var k in args) {
            if (val.hasOwnProperty(k)) {
                values[k] = args[k];
            }
        }
	};
	
	return {add:add};
}();

var htmlTemp = {
		li : '<li class="preMenuLi"/>',
		
		parentDiv : '<div class="parentList">'+
						'<input type="hidden" class="parentId" value="0" id="parent_id">'+
						'<dl style="width:30%"><input type="text" class="title" value="{0}"></dl>'+
						'<dl style="width:15%">'+
							'<select name="type" class="level">'+
								'<option value="click" class = "type_option_parent">关联关键字</option>'+
								'<option value="view" class = "type_option_parent">连接网站地址</option>'+
							 '</select>'+
						'</dl>'+
						'<dl style="width:20%"><input type="text" class="content" value="{1}"></dl>'+
						'<dl style="width:10%">'+
							'<select name="status" class="status" >'+
								'<option value="0" class="status_option_parent">停用</option>'+
								'<option value="1" class="status_option_parent">启用</option>'+
						    '</select>'+
					    '</dl>'+
						'<dl style="width:25%">'+
						     '<a href="###" class="aa detail toDelete" id="toDelete">删除</a>'+
						     '<a href="###" class="aa detail addSubMenu" id="addSubMenu">添加二级菜单</a>'+
						'</dl>'+
					'</div>'+
					'<div class="subArray"></div>'+
					'<div style="clear:both;"></div>',
					
		chlidDiv: '<div class="subList">'+
					'<input type="hidden" class="parentId" value="1" id="chlid_parent_id">'+
					'<dl style="width:30%;">'+
						'<img src="{0}/image/common/bg_repno_black.png" class="board">'+
						'<input type="text" class="title" value="{1}">'+
					'</dl>'+
					'<dl style="width:15%">'+
						 '<select name="type" class="level" id="type_id">'+
							'<option value="click" class = "type_option_chlid">关联关键字</option>'+
							'<option value="view" class = "type_option_chlid">连接网站地址</option>'+
						 '</select>'+
					'</dl>'+
					'<dl style="width:20%"><input type="text" class="content" value="{2}"></dl>'+
					'<dl style="width:10%">'+
						'<select name="status" class="status" id="status_id">'+
							'<option value="0" class="status_option_chlid">停用</option>'+
							'<option value="1" class="status_option_chlid">启用</option>'+
				        '</select>'+'</dl>'+
					'<dl style="width:25%">'+
						 '<a href="###" class="aa detail toDelete" id="toDelete">删除</a>'+
					'</dl>'+
				'</div>',
};