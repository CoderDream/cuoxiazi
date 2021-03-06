
function add() {
	// 取得用户输入的分类
	var categoryId = $("#categoryId").val();

	// 如果没有输入，则不响应确认按钮事件
	if (null == categoryId || '' == categoryId) {
		$("#categoryId").val("1");
	}

	$("[name='addForm']").attr("action", "add.action");
	$("[name='addForm']").attr("method", "post");
	$("[name='addForm']").submit();
}

function prePage() {
	var pageNow = $("#pageNow").val();
	pageNow = parseInt(pageNow) - 1;
	$("#pageNow").val(pageNow);
	var categoryId = $("#categoryId").val();
	$("#categoryId").val(categoryId);

	$("[name='pageForm']").attr("action", "list.action");
	$("[name='pageForm']").attr("method", "post");
	$("[name='pageForm']").submit();
}

function nextPage() {
	var pageNow = $("#pageNow").val();
	pageNow = parseInt(pageNow) + 1;
	$("#pageNow").val(pageNow);
	var categoryId = $("#categoryId").val();
	$("#categoryId").val(categoryId);

	$("[name='pageForm']").attr("action", "list.action");
	$("[name='pageForm']").attr("method", "post");
	$("[name='pageForm']").submit();
}

function load() {
	// 取得用户输入的页数
	var pageNow = $("#pageInput").val();
	// 如果没有输入，则不响应确认按钮事件
	if ('' != pageNow) {
		$("[name='pageForm']").attr("action", "list.action");
		$("[name='pageForm']").attr("method", "post");
		$("[name='pageForm']").submit();
	}
}

// 查询
function query() {
	// 取得用户输入的分类
	var categoryId = $("#categoryId").val();

	// 如果没有输入，则不响应确认按钮事件
	if (null == categoryId || '' == categoryId) {
		$("#categoryId").val("1");
	}

	$("[name='queryForm']").attr("action", "list.action");
	$("[name='queryForm']").attr("method", "post");
	$("[name='queryForm']").submit();
}

function calcShowModalDialogLocation(dialogWidth, dialogHeight) {
	var iWidth = dialogWidth;
	var iHeight = dialogHeight;
	var iTop = (window.screen.availHeight - 20 - iHeight) / 2;
	var iLeft = (window.screen.availWidth - 10 - iWidth) / 2;
	return 'dialogWidth:' + iWidth + 'px;dialogHeight:' + iHeight
			+ 'px;dialogTop: ' + iTop + 'px; dialogLeft: ' + iLeft
			+ 'px;center:yes;scroll:no;status:no;resizable:0;location:no';
}

// 上传
function openUploadPage() {
	if (window.ActiveXObject) { // IE
		var dialogLocation = calcShowModalDialogLocation(550, 600);
		var result = window.showModalDialog("../upload/upload.action", window,
				dialogLocation);

		if (returnValue != null) {
			setValue(returnValue);
		}
	} else { // 非IE
		var url = "../upload/upload.action"; // 转向网页的地址;
		var name; // 网页名称，可为空;
		var iWidth = 600; // 弹出窗口的宽度;
		var iHeight = 500; // 弹出窗口的高度;
		var iTop = (window.screen.availHeight - 30 - iHeight) / 2; // 获得窗口的垂直位置;
		var iLeft = (window.screen.availWidth - 10 - iWidth) / 2; // 获得窗口的水平位置;
		window
				.open(
						url,
						'newwindow',
						'height='
								+ iHeight
								+ ',,innerHeight='
								+ iHeight
								+ ',width='
								+ iWidth
								+ ',innerWidth='
								+ iWidth
								+ ',top='
								+ iTop
								+ ',left='
								+ iLeft
								+ ',toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no');
	}
}

// 接收父页面上传回的值
function setValue(newFileName) {
	$("newFileName").val(newFileName);
	$("#dish\\.picPath").val("/dish/" + newFileName);
	// 改变图片源，实时刷新图片
	$("#dishPic").attr("src", "../resources/images/dish/" + newFileName);
}

function save() {
	// 取得用户输入的分类
	var categoryId = $("#categoryId").val();

	// 如果没有输入，则不响应确认按钮事件
	if (null == categoryId || '' == categoryId) {
		$("#categoryId").val("1");
	}
	$("[name='saveForm']").attr("action", "save.action");
	$("[name='saveForm']").attr("method", "post");
	$("[name='saveForm']").submit();
}

function remove() {
	// 取得用户输入的分类
	var categoryId = $("#categoryId").val();

	// 如果没有输入，则不响应确认按钮事件
	if (null == categoryId || '' == categoryId) {
		$("#categoryId").val("1");
	}

	$("[name='saveForm']").attr("action", "remove.action");
	$("[name='saveForm']").attr("method", "post");
	$("[name='saveForm']").submit();
}

function update() {
	$("[name='updateForm']").attr("action", "update.action");
	$("[name='updateForm']").attr("method", "post");
	$("[name='updateForm']").submit();
}

function updateBack() {
	$("[name='updateForm']").attr("action", "list.action");
	$("[name='updateForm']").attr("method", "post");
	$("[name='updateForm']").submit();
}

function saveBack() {
	$("[name='saveForm']").attr("action", "list.action");
	$("[name='saveForm']").attr("method", "post");
	$("[name='saveForm']").submit();
}

// 获取窗口的高度
var windowHeight;
// 获取窗口的宽度
var windowWidth;
// 获取弹窗的宽度
var popWidth;
// 获取弹窗高度
var popHeight;
function init() {
	windowHeight = $(window).height();
	windowWidth = $(window).width();
	popHeight = $(".popwindow").height();
	popWidth = $(".popwindow").width();
}
// 关闭窗口的方法
function closeWindow() {
	$(".poptitle img").click(function() {
		$(this).parent().parent().hide("slow");
	});
}
// 定义弹出居中窗口的方法
function popCenterWindow() {
	init();
	// 计算弹出窗口的左上角Y的偏移量
	var popY = (windowHeight - popHeight) / 2;
	var popX = (windowWidth - popWidth) / 2;
	// alert("windowHeight: " + windowHeight + "; windowWidth:" + windowWidth
	// + "; popHeight: " + popHeight + "; popWidth:"
	// + popWidth + ": popY: " + popY
	// + "; popX:" + popX);
	// 设定窗口的位置
	$("#popwindowid").css("top", popY).css("left", popX).slideToggle("slow");
	closeWindow();
}

$(function() {
	var categoryId = $("#categoryId").val();
	$("#dishSelect").each(function() {
		$(this).children("option").each(function() {
			if (categoryId == $(this).val()) {
				$(this).attr("selected", true);
				return;
			}
		});
	});

	// 下拉选单值变事件
	$('#dishSelect').change(function() {
		var categoryId = $(this).children('option:selected').val();
		$("#categoryId").val(categoryId);
	});

	$("#btn_center").click(function() {
		popCenterWindow();
	});

});