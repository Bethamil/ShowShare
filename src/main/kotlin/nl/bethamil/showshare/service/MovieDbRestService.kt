package nl.bethamil.showshare.service

import nl.bethamil.showshare.Secrets
import nl.bethamil.showshare.model.SeasonData
import nl.bethamil.showshare.model.SerieData
import nl.bethamil.showshare.model.Show
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate

/**
 * @author Emiel Bloem
 *
 *
 * Dit doet het programma
 */


@Service
class MovieDbRestService(restTemplateBuilder: RestTemplateBuilder = RestTemplateBuilder()) {
    private val restTemplate: RestTemplate

    init {
        restTemplate = restTemplateBuilder.build()
    }


    fun getShows(category : String, pageNumber :  Int = -1): SerieData? {
        if (pageNumber == -1) {
            val url =
                "https://api.themoviedb.org/3/tv/{category}?api_key={apiKey}&language=en-US"
            return restTemplate.getForObject(url, SerieData::class.java, category, Secrets.movieDBKey)


        }

        val url =
            "https://api.themoviedb.org/3/tv/{category}?api_key={apiKey}&language=en-US&page={pageNumber}"
        return restTemplate.getForObject(url, SerieData::class.java, category, Secrets.movieDBKey, pageNumber)
    }

    fun getSingleShow(showId : Int) : Show? {
        val url =
            "https://api.themoviedb.org/3/tv/{showId}?api_key={apiKey}&language=en-US"
        return restTemplate.getForObject(url, Show::class.java, showId ,Secrets.movieDBKey)
    }

    fun getShowByQuery(showTitle : String) : SerieData? {
        val url =
            "https://api.themoviedb.org/3/search/tv?api_key={apiKey}&query={showTitle}"
        return restTemplate.getForObject(url, SerieData::class.java ,Secrets.movieDBKey, showTitle)
    }

    fun getSeasonInfo(showId: Int, seasonNumber : Int) : SeasonData? {
        val url = "https://api.themoviedb.org/3/tv/{showId}/season/{seasonNumber}?api_key={apiKey}&language=en-US"
        return restTemplate.getForObject(url, SeasonData::class.java, showId ,seasonNumber, Secrets.movieDBKey)
    }
}