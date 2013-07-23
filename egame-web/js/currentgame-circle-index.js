var currentGame = currentGame || {};
currentGame.circle = currentGame.circle || {};
(function(cuci,window,$){

    var USERID = "";

    var cuciin = window.cuciin = cuci.index = {
        init:function(){
            var self = this;
            USERID = GetQueryString("USERID");
            if(!USERID){
                self.failLoad("USERID is null");
            }
            //USERID = 100300;
            //发布新主题连接添加
            var publishKeyNoteUrl = String.format(circlePublishKeyNote_html,USERID);
            $("#btnPublish").attr("href",publishKeyNoteUrl);

            self.getDataAndShow();
        },
        pageSize:10,
        totalRecord:0,
        pageNum:1,
        totalPage:1,
        isDid:false,
        commMsg:{
            netErr:'网络异常，请检查网络',
            noList:'还没有主题。'
        },
        showCommMsg:function(msg){
            var str = '<div class="text_pk">'+msg+'</div>';
            $("#listCon").html(str);
        },
        failLoad:function(msg){
            showToast(msg);
        },
        toLink:function(url){
            window.location = url;
        },
        showUserDetail:function(e,userId){
            e.stopPropagation();
            showDetail('friendId',userId);
        },
        bindPageBtn:function(){
            var self = this;
            var jpageHome = $("#pageHome"), jpageUp = $("#pageUp"), jpageDown = $("#pageDown"), jpageEnd = $("#pageEnd");
            jpageHome.click(function(){
                self.pageJump("home");
            });
            jpageUp.click(function(){
                self.pageJump("up");
            });
            jpageDown.click(function(){
                self.pageJump("down");
            });
            jpageEnd.click(function(){
                self.pageJump("end");
            });
        },
        pageJump:function(type){
            var self = this;
            switch(type) {
                case 'home':
                    if(self.pageNum == 1){return false}
                    self.pageNum = 1;
                    self.getDataAndShow();
                    break;
                case 'up':
                    if(self.pageNum == 1){return false}
                    self.pageNum -= 1;
                    self.getDataAndShow();
                    break;
                case 'down':
                    if(self.pageNum == self.totalPage){return false}
                    self.pageNum += 1;
                    self.getDataAndShow();
                    break;
                case 'end':
                    if(self.pageNum == self.totalPage){return false}
                    self.pageNum = self.totalPage;
                    self.getDataAndShow();
                    break;
                default:
                    self.pageNum = 0;
                    self.getDataAndShow();
            }
        },
        fillData:function(data){
            var self = this;
            //getTotalPage返回总页码
            function getTotalPage(pageNum,pageSize,totalItem){
                var pageNum = pageNum,
                    pageSize = pageSize,
                    totalItem = totalItem;
                var totalPage = Math.ceil(totalItem/pageSize);
                if(totalPage === 0){
                    totalPage = 1;
                }
                return totalPage;
            }
            if(data.result.resultcode == "0"){
                if(!self.isDid){
                    self.totalRecord = data.totalRecored;
                    self.totalPage = getTotalPage(self.pageNum,data.pageSize,self.totalRecord);
                    self.bindPageBtn();
                    self.isDid = true;
                }
                //广告、主题数量
                if(data.advertPic !== null && data.advertLink !== null){
                    var adPic = data.advertPic;
                    $('#adImg').attr('src',adPic);
                    var adUrl = data.advertLink;
                    $('#ad').show();
                    $('#ad_hide').show();
                    $('#adImg').click(function(){
                        showSysBrowse(adUrl);
                    });
                }else{
                    $('#ad').hide();
                    $('#ad_hide').hide();
                }

                if(self.totalRecord == 0){
                    //如果数据总条数为0，显示没有数据提示并返回不再往下执行
                    self.showCommMsg(self.commMsg.noList);
                    return false;
                }

                $("#numMsg").removeClass("hide");
                $("#countnum").html(self.totalRecord);
                //setPagesState设置分页按钮显示状态
                setPagesState(self.pageNum,self.totalPage);
                var str = "",subjectList = data.subjectList;
                var suserDetail = "";
                for(var i in subjectList){
                    var item = subjectList[i];
                    //var subjectUrl = function(e){e.preventDefault();event.stopPropagation();alert(1)};
                    var subjectUrl = String.format(currentgameCircleDetail_html,item.subjectId,USERID);
                    //suserDetail = 'showDetail(\'friendId\','+item.senderId+')';
                    str += '<div class="line_1"></div><div class="list pos"><a onclick="cuciin.toLink(\''+subjectUrl+'\')" class="coll_bg P_tb_10 blohi">';
                    str += '<div class="collLeft pos"><span onclick="cuciin.showUserDetail(event,\''+item.senderId+'\')" class="head_mask_76c"></span><img src="'+item.senderIcon+'" alt=""/></div>';
                    str += '<div class="listRight">';
                    str += '<p class="font_20 m_tb_2 color_10"><span onclick="cuciin.showUserDetail(event,\''+item.senderId+'\')">'+item.senderName+'</span>：<span class="font_18 color_2">'+item.subjectTittle+'</span></p>';
                    str += '<p class="font_18 color_4 m_tb_2 p_r_30">'+item.subjectContent+'</p>';
                    str += '<p class="font_18 color_4 m_tb_2 p_r_30">'+item.sendTime;
                    if(item.repposterName == null || item.repposterName == "null"){
                        str += '<span class="m_l_20">还没有最新的回复</span>';
                    }else{
                        str += '<span onclick="cuciin.showUserDetail(event,\''+item.repposterId+'\')" class="m_l_20 color_1">'+item.repposterName+'</span>最后回复';
                    }

                    str += '</p></div><div class="narrow_r"></div>';
                    str += '</a></div>';
                }
                $("#listCon").html(str);
            }else {
                self.showCommMsg(data.result.resultmsg);
            }
        },
        getDataAndShow:function(){
            var self = this, startIndex = (self.pageNum-1)*self.pageSize;
            var url = String.format(currentGameCircleIndex_url,startIndex,self.pageSize);
            printToConsole("============================================================");
            printToConsole(url)
            var request = $.ajax({
                url : url,
                success : function(data) {
                    pageLoadFinish();
                    self.fillData(data);
                },
                dataType : "json"
            });

            request.fail(function(){
                pageLoadFinish();
                self.failLoad(self.commMsg.netErr);
            });
        }
    };

    // 页面读取完毕的时候，初始化数据
    $(document).ready(function(){
        cuciin.init();
    });

})(currentGame.circle,window,jQuery);
