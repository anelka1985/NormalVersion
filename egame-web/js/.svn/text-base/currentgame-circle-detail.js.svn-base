var currentGame = currentGame || {};
currentGame.circle = currentGame.circle || {};

(function(cuci,window,$){

    var USERID = "";
    var subjectId = "";
    var cucide = window.cucide = currentGame.circle.detail = {
        init:function(){
            USERID = GetQueryString("USERID");
            subjectId = GetQueryString("subjectId");
            if(!USERID || !subjectId){
                self.failLoad("参数异常");
            }
            $("#btnBack").attr("href",String.format(currentGameCircleIndex_html,USERID));
            this.getDataAndShow();
        },
        pageSize:10,
        totalRecord:0,
        pageNum:1,
        totalPage:1,
        isDid:false,
        commMsg:{
            netErr:'网络异常，请检查网络',
            noList:'还没人回复。'
        },
        showCommMsg:function(msg){
            var str = '<p style="padding:0 0 20px 0;font-size:20px;text-align:center">'+msg+'</p>';
            $("#repCon").html(str);
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
        failLoad:function(msg){
            showToast(msg);
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
                var _subject = data.subjectDetail;
                if(!self.isDid){
                    self.totalRecord = _subject.totalReply;
                    self.totalPage = getTotalPage(self.pageNum,data.pageSize,self.totalRecord);
                    self.bindPageBtn();
                    self.isDid = true;
                }
                //var pageData = getPageData(self.startIndex,data.pageSize,_totalReply);
                //主题信息
                $("#icon_box").removeClass("hide_gone");
                $("#msg_box").removeClass("hide_gone");
                $("#userInfo").click(function(){showDetail("friendId",_subject.senderId);});
                $("#subjectIco").click(function(){showDetail("friendId",_subject.senderId);});
                $("#subjectIco").append('<img src="'+_subject.senderIcon+'" />');
                $("#subjectTitle").html(_subject.subjectTittle);
                $("#sendTime").html(_subject.sendTime);
                $("#subjectContent").html(_subject.subjectContent);
                //发布回复按钮
                $("#btnRe").attr("href",String.format(currentGameReply_html,subjectId,USERID));
                if(self.totalRecord == 0){
                    //如果数据总条数为0，显示没有数据提示并返回不再往下执行
                    self.showCommMsg(self.commMsg.noList);
                    return false;
                }
                $("#repNum").removeClass("hide");
                $("#repNum").html('共有<span class="color_1">'+self.totalRecord+'</span>个回复');
                //setPagesState设置分页按钮显示状态
                setPagesState(self.pageNum,self.totalPage);
                var html = '',replayList = data.replayList;
                var srepposterDetail = '';
                for(var i in replayList){
                    var item = replayList[i];
                    srepposterDetail = 'showDetail(\'friendId\','+item.repposterId+')';
                    html += '<div class="line_1"></div><div class="list pos sym_bg"><div class="P_tb_10 blohi">';
                    html += '<div class="collLeft pos"><a onclick="'+srepposterDetail+'"><span class="head_mask_76c"></span><img src="'+item.repposterIcon+'" alt=""/></a></div>';
                    html += '<div class="listRight">';
                    html += '<P class="font_20 P_tb_5"><a onclick="'+srepposterDetail+'">'+item.repposterName+'</a> <span></span> <span class="font_18 color_4 float_r">'+item.replyTime+'</span></P>';
                    html += '<P class="font_18 color_2 m_tb_2 p_r_10">'+item.repcontent+'</P>';
                    html += '</div></div>';
                    html += '</div>';
                }
                $("#repCon").html(html);
            }else {
                self.showCommMsg(data.result.resultmsg);
            }
        },
        getDataAndShow:function(){
            var self = this, startIndex = (self.pageNum-1)*self.pageSize;
            var url = String.format(currentGameCircleDetail_url,subjectId,startIndex,self.pageSize);
            var request = $.ajax({
                url : url,
                type : "GET",
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
        cucide.init();
    });

})(currentGame.circle,window,jQuery)
