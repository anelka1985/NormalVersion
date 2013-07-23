//设置分页按钮样式具体实现
var setBtnState = (function(){
    function setBtnState(selecter,state){
        //state:0表示不可点状态，1表示可点状态
        var jbtn = $(selecter);
        if(state === 0){
            jbtn.removeClass().addClass("btu_page btu_gra_s pos btu_page_com");
            jbtn.find(".btu_green_s_left").removeClass().addClass("btu_man_com2 btu_gra_s_com btu_gra_s_left");
            jbtn.find(".btu_green_s_right").removeClass().addClass("btu_gra_s_right");
        }else if(state === 1){
            jbtn.removeClass().addClass("btu_page btu_green_s pos");
            jbtn.find(".btu_gra_s_left").removeClass().addClass("btu_man_com2 btu_green_s_com btu_green_s_left");
            jbtn.find(".btu_gra_s_right").removeClass().addClass("btu_green_s_right");
        }

    }

    return function(obj){
        //obj结构{选择器内容:按钮状态}
        for(var k in obj){
            setBtnState(obj[k].jbtn,obj[k].state);
        }
    }
})();
//设置分页按钮状态
//btnList为按钮jquery对象列表，不设置则使用默认值（固定id获取）
//btnCon为分页按钮外部包裹对象，不设置则使用默认值（id为pageCon的对象）
var setPagesState = function(pageNum,totalPage,btnList,btnCon){
    $(window).scrollTop(0);
    var btnCon = btnCon || $("#pageCon");
    var pageHome = $("#pageHome"),
        pageUp = $("#pageUp"),
        pageDown = $("#pageDown"),
        pageEnd = $("#pageEnd");
    if(!!btnList){
        pageHome = btnList.pageHome;
        pageUp = btnList.pageUp;
        pageDown = btnList.pageDown;
        pageEnd = btnList.pageEnd;
    }
    if(pageNum == 1){
        if(pageNum == totalPage){
            $("#pageCon").hide();
        }else{
            setBtnState([{"jbtn":pageHome,"state":0},{"jbtn":pageUp,"state":0},{"jbtn":pageDown,"state":1},{"jbtn":pageEnd,"state":1}]);
            pageHome.find("a").removeAttr("href");
            pageUp.find("a").removeAttr("href");
            $("#pageCon").show();
        }
    }else if(pageNum >= totalPage){
        setBtnState([{"jbtn":pageHome,"state":1},{"jbtn":pageUp,"state":1},{"jbtn":pageDown,"state":0},{"jbtn":pageEnd,"state":0}]);
        pageDown.find("a").removeAttr("href");
        pageEnd.find("a").removeAttr("href");
        $("#pageCon").show();
    }else{
        setBtnState([{"jbtn":pageHome,"state":1},{"jbtn":pageUp,"state":1},{"jbtn":pageDown,"state":1},{"jbtn":pageEnd,"state":1}]);
        $("#pageCon").show();
    }
}

