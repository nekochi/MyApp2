package com.nekomimi.bean;

import java.util.List;

/**
 * Created by hongchi on 2015-11-30.
 * File description :
 */
public class NewsInfo
{

    private PageBean pagebean;



    public PageBean getPagebean()
    {
        return this.pagebean;
    }
    public void setPagebean(PageBean pagebean)
    {
        this.pagebean = pagebean;
    }
//    @Override
//    public String toString()
//    {
//        return new StringBuilder("{ currentPage:").append(currentPage).append(",maxResult:").append(maxResult).append(pagebean.toString()).append("}").toString();
//    }



    public static class PageBean
    {
        private int allNum;
        private int allPages;
        private int currentPage;
        private int maxResult;
        private List<News> contentlist;

        public int getAllNum()
        {
            return this.allNum;
        }
        public void setAllNum(int allNum)
        {
            this.allNum = allNum;
        }
        public int getCurrentPage()
        {
            return currentPage;
        }
        public void setCurrentPage(int currentPage)
        {
            this.currentPage = currentPage;
        }

        public int getMaxResult()
        {
            return this.maxResult;
        }
        public void setMaxResult(int maxResult)
        {
            this.maxResult = maxResult;
        }

        public int getAllPages()
        {
            return this.allPages;
        }
        public void setAllPages(int allPages)
        {
            this.allPages = allPages;
        }

        public List<News> getContentlist()
        {
            return this.contentlist;
        }
        public void setContentlist(List<News> contentlist)
        {
            this.contentlist = contentlist;
        }

        @Override
        public String toString()
        {
            return new StringBuffer("{ allNum:").append(allNum).append(",allPages:").append(allPages).append(",contentList:").append(contentlist.toString()).append("}").toString();
        }
    }

    public static class News
    {
        private String channelId;
        private String channelName;
        private int chinaJoy;
        private String desc;
        private List<ImageInfo> imageurls;
        private String link;
        private String long_abs;
        private String nid;
        private String pubDate;
        private String source;
        private String title;

        public String getChannelId()
        {
            return this.channelId;
        }
        public void setChannelId(String channelId)
        {
            this.channelId = channelId;
        }

        public String getChannelName()
        {
            return this.channelName;
        }
        public void setChannelName(String channelName)
        {
            this.channelName = channelName;
        }

        public int getChinaJoy()
        {
            return this.chinaJoy;
        }
        public void setChinaJoy(int chinaJoy)
        {
            this.chinaJoy = chinaJoy;
        }

        public String getDesc()
        {
            return this.desc;
        }
        public void setDesc(String desc)
        {
            this.desc = desc;
        }

        public List<ImageInfo> getImageurls()
        {
            return this.imageurls;
        }
        public void setImageurls(List<ImageInfo> imageurls)
        {
            this.imageurls = imageurls;
        }

        public String getLink()
        {
            return this.link;
        }
        public void setLink(String link)
        {
            this.link = link;
        }

        public String getLong_abs()
        {
            return this.long_abs;
        }
        public void setLong_abs(String long_abs)
        {
            this.long_abs = long_abs;
        }
        public String getNid()
        {
            return this.nid;
        }
        public void setNid(String nid)
        {
            this.nid = nid;
        }

        public String getPubDate()
        {
            return this.pubDate;
        }
        public void setPubDate(String pubDate)
        {
            this.pubDate = pubDate;
        }

        public String getSource()
        {
            return this.source;
        }
        public void setSource(String source)
        {
            this.source = source;
        }

        public String getTitle()
        {
            return this.title;
        }
        public void setTitle(String title)
        {
            this.title = title;
        }
        @Override
        public String toString()
        {
            return new StringBuilder("{ channelId:").append(channelId).append(",channelName:").append(channelName).append(",chinaJoy:").append(chinaJoy)
                    .append(",desc:").append(desc).append(",imageurls:").append(imageurls.toString()).append(",link:").append(link).append(",long_abs:")
                    .append(long_abs).append(",nid:").append(nid).append(",pubDate:").append(pubDate).append(",source:").append(source).append(",title:")
                    .append(title).toString();
        }
    }

    public static class ImageInfo
    {
        private String url;
        private int height;
        private int width;

        public String getUrl()
        {
            return this.url;
        }
        public void setUrl(String url)
        {
            this.url = url;
        }

        public int getHeight()
        {
            return this.height;
        }
        public void setHeight(int height)
        {
            this.height = height;
        }

        public int getWidth()
        {
            return this.width;
        }
        public void setWidth(int width)
        {
            this.width = width;
        }
    }
}
