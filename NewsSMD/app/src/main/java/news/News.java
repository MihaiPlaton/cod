package news;

public class News {
    private final String mtitle;
    private final String murl;
    private final String mthumbnail;
    private final String mtrailtexthtml;
    private final String msection;
    private final String mauthor;
    private final String mdate;


    public News(String title, String section, String author, String date, String url, String thumbnail, String trailText) {
        mtitle = title;
        murl = url;
        mthumbnail = thumbnail;
        mtrailtexthtml = trailText;
        msection = section;
        mauthor = author;
        mdate = date;

    }

    public String getTitle() {
        return mtitle;
    }
    public String getUrl() {
        return murl;
    }
    public String getThumbnail() {
        return mthumbnail;
    }
    public String getTrailTextHtml() {
        return mtrailtexthtml;
    }
    public String getSection() {
        return msection;
    }
    public String getAuthor() {
        return mauthor;
    }
    public String getDate() {
        return mdate;
    }

}
