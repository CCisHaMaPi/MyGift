package com.lishijia.my.mygift.entities;

/**
 * Created by my on 2016/12/28.
 */

public class NetUrl {
    /**
     * 图片地址前缀
     */
    public static final String BEFORE_URL = "http://www.1688wan.com";
    /**
     * 礼包接口
     */
    public static final String GIFT_LIST_BEAN =
            "http://www.1688wan.com/majax.action?method=getGiftList&pageno=";
    /**
     * 礼包详情接口
     */
    public static final String GIFT_LIST_BEAN_INFO =
            "http://www.1688wan.com/majax.action?method=getGiftInfo&id=";
    /**
     * 游戏开服接口
     */
    public static final String GAME_SERVICE_LIST =
            "http://www.1688wan.com/majax.action?method=getJtkaifu";
    /**
     * 游戏开测接口
     */
    public static final String GAME_TEXT_LIST =
            "http://www.1688wan.com/majax.action?method=getWebfutureTest";
    /**
     * 新游周刊接口
     */
    public static final String STYLE_WEEKLY_LIST =
            "http://www.1688wan.com/majax.action?method=getWeekll&pageNo=0";
    /**
     * 暴打星期三接口
     */
    public static final String STYLE_WEDNESDAY_LIST =
            "http://www.1688wan.com/majax.action?method=bdxqs&pageNo=0";
    /**
     * 热门接口
     */
    public static final String HOT_LIST =
            "http://www.1688wan.com//majax.action?method=hotpushForAndroid";

}
