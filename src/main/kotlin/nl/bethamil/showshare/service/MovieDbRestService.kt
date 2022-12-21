package nl.bethamil.showshare.service

import nl.bethamil.showshare.model.SeasonData
import nl.bethamil.showshare.model.SerieData
import nl.bethamil.showshare.model.Show
import nl.bethamil.showshare.tmdbApi.ShowVideos
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate


@Service
class MovieDbRestService(restTemplateBuilder: RestTemplateBuilder = RestTemplateBuilder()) {
    private final val MOVIEDBKEY = System.getenv("movieDBKey")
    private val restTemplate: RestTemplate

    init {
        restTemplate = restTemplateBuilder.build()
    }


    fun getShows(category : String, pageNumber :  Int = -1): SerieData? {
        if (pageNumber == -1) {
            val url =
                "https://api.themoviedb.org/3/tv/{category}?api_key={apiKey}&language=en-US"
            return restTemplate.getForObject(url, SerieData::class.java, category, MOVIEDBKEY)
        }

        val url =
            "https://api.themoviedb.org/3/tv/{category}?api_key={apiKey}&language=en-US&page={pageNumber}"
        return restTemplate.getForObject(url, SerieData::class.java, category, MOVIEDBKEY, pageNumber)
    }

    fun getSingleShow(showId : Int) : Show? {
        val url =
            "https://api.themoviedb.org/3/tv/{showId}?api_key={apiKey}&language=en-US"
        return restTemplate.getForObject(url, Show::class.java, showId ,MOVIEDBKEY)
    }

    fun getShowByQuery(showTitle : String) : SerieData? {
        val url =
            "https://api.themoviedb.org/3/search/tv?api_key={apiKey}&query={showTitle}"
        return restTemplate.getForObject(url, SerieData::class.java ,MOVIEDBKEY, showTitle)
    }

    fun getSeasonInfo(showId: Int, seasonNumber : Int) : SeasonData? {
        val url = "https://api.themoviedb.org/3/tv/{showId}/season/{seasonNumber}?api_key={apiKey}&language=en-US"
        return restTemplate.getForObject(url, SeasonData::class.java, showId ,seasonNumber, MOVIEDBKEY)
    }

    fun getShowTrailer(showId: Int) : ShowVideos? {
        val url = "https://api.themoviedb.org/3/tv/{showId}/videos?api_key={apiKey}&language=en-US"
        return restTemplate.getForObject(url, ShowVideos::class.java, showId, MOVIEDBKEY)
    }

    fun getSeasonTrailer(showId: Int, seasonNumber : Int) : ShowVideos? {
        val url = "https://api.themoviedb.org/3/tv/{showId}/season/{seasonNumber}/videos?api_key={apiKey}&language=en-US"
        return restTemplate.getForObject(url, ShowVideos::class.java, showId, seasonNumber, MOVIEDBKEY)
    }
}