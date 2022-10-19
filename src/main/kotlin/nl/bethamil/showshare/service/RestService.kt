package nl.bethamil.showshare.service

import nl.bethamil.showshare.Secrets
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
class RestService(restTemplateBuilder: RestTemplateBuilder) {
    private val restTemplate: RestTemplate

    init {
        restTemplate = restTemplateBuilder.build()
    }


    fun getPopulairSeries(pageNumber : Int): SerieData? {
        val url =
            "https://api.themoviedb.org/3/tv/popular?api_key={apiKey}&language=en-US&page={pageNumber}"
        return restTemplate.getForObject(url, SerieData::class.java, Secrets.movieDBKey, pageNumber)
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
}