package nl.bethamil.showshare.viewmodel

import nl.bethamil.showshare.model.*

interface ModelViewMapper {
    fun SerieData.toAllShowsListVM() = AllShowsListVM(results)
    fun ShowCollection.toShowCollectionVM() = ShowCollectionVM(id, user, showId)
    fun SeasonData.toSeasonDataVM() = SeasonDataVM(_id, air_date, episodes, name, overview, id, poster_path, season_number)
    fun RatingShow.toRatinVM() = RatingVM(ratingId, user, rating, showId)
    fun Show.toShowVM() = ShowVM(
        id, first_air_date, last_air_date, backdrop_path, name, original_name, overview, popularity, poster_path,
        vote_average, vote_count, homepage, seasons, genres
    )

    fun WatchedEpisodeVM.toModel() = WatchedEpisode(viewDateId, showShareUser, showId, seasonNumber, episodeNumber)
    fun WatchedEpisode.toVm() = WatchedEpisodeVM(viewDateId, showShareUser, showId, seasonNumber, episodeNumber)
}