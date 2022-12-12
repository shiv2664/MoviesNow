package com.shivam.moviesnow.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Entity
@Parcelize
data class MovieList (
    @PrimaryKey(autoGenerate = false)
    @SerializedName("IMDBID")          val IMDBID   :       String = "",
    @SerializedName("Title")           val Title    :       String ="",
    @SerializedName("Year")            val Year     :       String? = null,
    @SerializedName("Summary")         val Summary  :       String? = null,
    @SerializedName("Short Summary")   val ShortSummary : String? = null,
    @SerializedName("Genres")          val Genres   :       String? = null,
    @SerializedName("Runtime")         val Runtime  :       String? = null,
    @SerializedName("YouTube Trailer") val YouTubeTrailer : String? = null,
    @SerializedName("Rating" )         val Rating   :       String? = null,
    @SerializedName("Movie Poster")    val MoviePoster  : String? = null,
    @SerializedName("Director")        val Director :       String? = null,
    @SerializedName("Writers")         val Writers  :       String? = null,
    @SerializedName("Cast")            val Cast     :       String? = null
):Parcelable{
    override fun equals(other: Any?): Boolean {

        if (javaClass != other?.javaClass) {
            return false
        }

        other as MovieList

        if (Title != other.Title) {
            return false
        }
        if (Year != other.Year) {
            return false
        }
        if (Summary   != other.Summary  ) {
            return false
        }
        if (ShortSummary != other.ShortSummary) {
            return false
        }
        if (Genres != other.Genres) {
            return false
        }
        if (IMDBID  != other.IMDBID ) {
            return false
        }
        if (Runtime != other.Runtime) {
            return false
        }
        if (YouTubeTrailer!= other.YouTubeTrailer) {
            return false
        }
        if (Rating!= other.Rating) {
            return false
        }
        if (MoviePoster  != other.MoviePoster) {
            return false
        }

        if (Director != other.Director) {
            return false
        }

        if (Writers != other.Writers) {
            return false
        }

        if (Cast != other.Cast) {
            return false
        }
        return true
    }

    override fun hashCode(): Int {
        var result = Title.hashCode()
        result = 31 * result + (Year?.hashCode() ?: 0)
        result = 31 * result + (Summary?.hashCode() ?: 0)
        result = 31 * result + (ShortSummary?.hashCode() ?: 0)
        result = 31 * result + (Genres?.hashCode() ?: 0)
        result = 31 * result + IMDBID.hashCode()
        result = 31 * result + (Runtime?.hashCode() ?: 0)
        result = 31 * result + (YouTubeTrailer?.hashCode() ?: 0)
        result = 31 * result + (Rating?.hashCode() ?: 0)
        result = 31 * result + (MoviePoster?.hashCode() ?: 0)
        result = 31 * result + (Director?.hashCode() ?: 0)
        result = 31 * result + (Writers?.hashCode() ?: 0)
        result = 31 * result + (Cast?.hashCode() ?: 0)
        return result
    }
}