//  本文件用于定义 所有页面共通的 显示部分的 东西

// 向某个标签插入 loading
function  addLoadingTo(dest_id, id_name){
	str = '	<div id ='+id_name+'><p style="text-align: center"><img src="images/progress.gif" alt=""/>内容加载中……</p></div>';
	$("#"+dest_id).append(str);		
}

// 向某个标签插入 load err
function  addLoadErrTo(dest_id){
	str = '<div class="text_pk"><p>加载失败，请重新再试一次。</p></div>';
	$("#"+dest_id).append(str);		
}


//字符串格式化函数：
function format(str) {
    var args = arguments, re = new RegExp("%([1-" + args.length + "])", "g");
    return String(str).replace(
        re,
        function($1, $2) {
            return args[$2];
        }
    );
};
