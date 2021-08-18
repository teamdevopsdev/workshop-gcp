/*
 *      Copyright (c) 2004-2016 Stuart Boston
 *
 *      This file is part of TheMovieDB API.
 *
 *      TheMovieDB API is free software: you can redistribute it and/or modify
 *      it under the terms of the GNU General Public License as published by
 *      the Free Software Foundation, either version 3 of the License, or
 *      any later version.
 *
 *      TheMovieDB API is distributed in the hope that it will be useful,
 *      but WITHOUT ANY WARRANTY; without even the implied warranty of
 *      MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *      GNU General Public License for more details.
 *
 *      You should have received a copy of the GNU General Public License
 *      along with TheMovieDB API.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package br.com.tiagohs.popmovies.model.media;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author Stuart
 */
public class Translation implements Parcelable {

    @JsonProperty("english_name")
    private String englishName;
    @JsonProperty("iso_639_1")
    private String language;
    @JsonProperty("name")
    private String name;
    @JsonProperty("iso_3166_1")
    private String country;
    @JsonProperty("data")
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getEnglishName() {
        return englishName;
    }

    public String getLanguage() {
        return language;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public void setEnglishName(String englishName) {
        this.englishName = englishName;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Translation) {
            final Translation other = (Translation) obj;
            return new EqualsBuilder()
                    .append(name, other.name)
                    .append(englishName, other.englishName)
                    .append(language, other.language)
                    .isEquals();
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(englishName)
                .append(language)
                .append(name)
                .toHashCode();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.SIMPLE_STYLE);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.englishName);
        dest.writeString(this.language);
        dest.writeString(this.name);
        dest.writeString(this.country);
    }

    public Translation() {
    }

    protected Translation(Parcel in) {
        this.englishName = in.readString();
        this.language = in.readString();
        this.name = in.readString();
        this.country = in.readString();
    }

    public static final Parcelable.Creator<Translation> CREATOR = new Parcelable.Creator<Translation>() {
        @Override
        public Translation createFromParcel(Parcel source) {
            return new Translation(source);
        }

        @Override
        public Translation[] newArray(int size) {
            return new Translation[size];
        }
    };
}
