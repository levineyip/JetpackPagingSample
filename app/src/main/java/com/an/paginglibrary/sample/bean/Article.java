package com.an.paginglibrary.sample.bean;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;

import java.util.Objects;

public class Article implements Parcelable {

    private int id;
    private String author;
    private String title;
    private String desc;
    private String envelopePic;
    private String link;
    private String niceDate;

    protected Article(Parcel in) {
        id = in.readInt();
        author = in.readString();
        title = in.readString();
        desc = in.readString();
        envelopePic = in.readString();
        link = in.readString();
        niceDate = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(author);
        dest.writeString(title);
        dest.writeString(desc);
        dest.writeString(envelopePic);
        dest.writeString(link);
        dest.writeString(niceDate);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Article> CREATOR = new Creator<Article>() {
        @Override
        public Article createFromParcel(Parcel in) {
            return new Article(in);
        }

        @Override
        public Article[] newArray(int size) {
            return new Article[size];
        }
    };



    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getEnvelopePic() {
        return envelopePic;
    }

    public void setEnvelopePic(String envelopePic) {
        this.envelopePic = envelopePic;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getNiceDate() {
        return niceDate;
    }

    public void setNiceDate(String niceDate) {
        this.niceDate = niceDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public static DiffUtil.ItemCallback<Article> DIFF_CALLBACK = new DiffUtil.ItemCallback<Article>() {
        @Override
        public boolean areItemsTheSame(@NonNull Article oldItem, @NonNull Article newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Article oldItem, @NonNull Article newItem) {
            return (oldItem.equals(newItem));
        }
    };

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Article article = (Article) o;
        return id == article.id &&
                Objects.equals(author, article.author) &&
                Objects.equals(title, article.title) &&
                Objects.equals(desc, article.desc) &&
                Objects.equals(envelopePic, article.envelopePic) &&
                Objects.equals(link, article.link) &&
                Objects.equals(niceDate, article.niceDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, author, title, desc, envelopePic, link, niceDate);
    }
}
