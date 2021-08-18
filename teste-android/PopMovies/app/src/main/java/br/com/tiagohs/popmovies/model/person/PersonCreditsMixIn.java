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
package br.com.tiagohs.popmovies.model.person;

import android.os.Parcel;
import android.os.Parcelable;

import com.fasterxml.jackson.annotation.JsonSetter;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

import java.util.List;

import br.com.tiagohs.popmovies.model.credits.CreditBasic;
import br.com.tiagohs.popmovies.model.credits.CreditMovieBasic;
import br.com.tiagohs.popmovies.model.credits.CreditTVBasic;

public class PersonCreditsMixIn implements Parcelable {

    @JsonTypeInfo(
            use = JsonTypeInfo.Id.NAME,
            include = JsonTypeInfo.As.PROPERTY,
            property = "media_type",
            defaultImpl = CreditBasic.class
    )
    @JsonSubTypes({
        @JsonSubTypes.Type(value = CreditMovieBasic.class, name = "movie"),
        @JsonSubTypes.Type(value = CreditTVBasic.class, name = "tv")
    })
    @JsonSetter("cast")
    public void setCast(List<CreditBasic> cast) {
        // Mixin empty class
    }

    @JsonTypeInfo(
            use = JsonTypeInfo.Id.NAME,
            include = JsonTypeInfo.As.PROPERTY,
            property = "media_type",
            defaultImpl = CreditBasic.class
    )
    @JsonSubTypes({
        @JsonSubTypes.Type(value = CreditMovieBasic.class, name = "movie"),
        @JsonSubTypes.Type(value = CreditTVBasic.class, name = "tv")
    })
    @JsonSetter("crew")
    public void setCrew(List<CreditBasic> crew) {
        // Mixin empty class
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public PersonCreditsMixIn() {
    }

    protected PersonCreditsMixIn(Parcel in) {
    }

    public static final Parcelable.Creator<PersonCreditsMixIn> CREATOR = new Parcelable.Creator<PersonCreditsMixIn>() {
        @Override
        public PersonCreditsMixIn createFromParcel(Parcel source) {
            return new PersonCreditsMixIn(source);
        }

        @Override
        public PersonCreditsMixIn[] newArray(int size) {
            return new PersonCreditsMixIn[size];
        }
    };
}
