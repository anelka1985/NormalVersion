var currentGame = currentGame || {};
currentGame.achievement = currentGame.achievement || {};

(function(cuac,window,$){
    var USERID = "";
    var userType = 1;
    var gameId = "";
    var achieveId = null;

    var cuacre = window.cuacre = cuac.reached = {
        init:function(){
            var self = this;
            var _jtabLists = $("#listTab>li");
            USERID = GetQueryString("USERID");
            gameId = GetQueryString("gameId");
            userType = Number( GetQueryString("userType") || 1);
            achieveId = GetQueryString("achieveId");

            //tab切换处理
            function changeTab(tabList,curTabIndex,otherTabIndex,userType){
                var curTab = tabList.eq(curTabIndex);
                var otherTab = tabList.eq(otherTabIndex);
                curTab.attr("class","title_pk_select").find("a").removeAttr("href");
                curTab.find(".head_mask_pknarrow").show();
                otherTab.attr("class","title_pk_nor").find("a").attr("href",String.format(currentGameAchievementReached_html,USERID,userType,achieveId,gameId));
                otherTab.find(".head_mask_pknarrow").hide();

            }
            //以userType判断调用changeTab函数
            if(userType == 1){
                changeTab(_jtabLists,0,1,0);
            }else{
                changeTab(_jtabLists,1,0,1);
            }

            //数据加载入口
            self.getDataAndShow();
        },
        pageSize:15,
        totalRecord:0,
        pageNum:1,
        totalPage:1,
        isDid:false,
        commMsg:{
            netErr:'网络异常，请检查网络',
        },
        showCommMsg:function(msg){
            var str = '<div class="text_pk">'+msg+'</div>';
            $("body").append(str);
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
                    break;
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
                    self.totalRecord = data.totalRecord;
                    self.totalPage = getTotalPage(self.pageNum,data.pageSize,self.totalRecord);
                    self.bindPageBtn();
                    self.isDid = true;
                }
                //好友数量
                $("#numMsg").removeClass("hide");
                if(userType == 1){
                    $("#numMsg").html('共有<span class="color_1">'+self.totalRecord+'</span>个好友达成此成就');
                }else{
                    $("#numMsg").html('共有<span class="color_1">'+self.totalRecord+'</span>个用户达成此成就');
                }
                //$("#friendsNum").html(_totalRecord);
                //分页按钮连接设置
                setPagesState(self.pageNum,self.totalPage);

                var str = "";
                var userDetail = "";
                for(var i in data.achieveUserList){
                    var item = data.achieveUserList[i];
                    userDetail = 'showDetail(\'friendId\','+item.userId+')';
                    str += '<div class="line_1"></div>';
                    str += '<div class="list pos"><a onclick="'+userDetail+'" class="coll_bg P_tb_10 blohi">';
                    str += '<div class="collLeft pos"><span class="head_mask_76c"></span><img src="'+item.userIcon+'" alt=""/></div>';
                    str += '<div class="listRight">';
                    str += '<P class="font_20 m_tb_2">'+item.userName+'</P>';
                    str += '<P class="font_18 color_4 m_tb_2 P_t_20">'+item.reachTime+' 达成</P>';
                    str += '</div>';
                    str += '<div class="narrow_r"></div>';
                    str += '</a></div>';
                }
                $("#achievementReachedCon").html(str);
            }else {
                if(userType == 1){
                	if(data.gangProperties == 1){
                		self.showCommMsg('<div class="text_pk" id="nofriend">还没有朋友玩过此款游，邀请朋友一起来玩哦 ！<div class="btu_gr pos m_t_20"><a href="'+ String.format(currentGameShare_html,USERID,"undefined") + '"><span class="btu_gr_l"></span><span class="btu_gr_r"></span>邀请朋友</a></div></div>');
                	}else{
                		self.showCommMsg('<div class="fri_text">您还没有好友哦，立即去找朋友吧</div><div class="br_color3 p_10"><div class="btu_gr pos btu_gr_100"><a href="'+String.format(findfri_html,USERID)+'"><span class="btu_gr_l"></span> <span class="btu_gr_r"></span>找朋友  </a></div></div>');
                	}
                }else{
                    self.showCommMsg('<div class="text_pk" id="nofriend">还没有朋友玩过此款游戏，邀请朋友一起来玩哦 ！<div class="btu_gr pos m_t_20"><a href="'+ String.format(currentGameShare_html,USERID,"undefined") + '"><span class="btu_gr_l"></span><span class="btu_gr_r"></span>邀请朋友</a></div></div>');
                }
                //self.showCommMsg(data.result.resultmsg);
            }
        },
        getDataAndShow:function(){
            var self = this, startIndex = (self.pageNum-1)*self.pageSize;
            var url = currentGameAchievementReached_url;
            url = String.format(url,USERID,gameId,achieveId,userType,startIndex,self.pageSize);
            printToConsole(url);
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
                success="1";
                showToast(self.commMsg.netErr);
            });
        }
    };

    // 页面读取完毕的时候，初始化数据
    $(document).ready(function(){
        cuacre.init();
    });

})(currentGame.achievement,window,jQuery);
