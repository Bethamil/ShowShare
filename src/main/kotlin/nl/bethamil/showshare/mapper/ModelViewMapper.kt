package nl.bethamil.showshare.mapper

import nl.bethamil.showshare.model.*
import nl.bethamil.showshare.viewmodel.*

interface ModelViewMapper {
    fun MyFavorites.toVM() = MyFavoritesVM(id, user.toVMLazy(), showId)
    fun MyFavoritesVM.toModel() = MyFavorites(id, user.toModel(), showId)

    fun RatingShow.toVM() = RatingShowVM(ratingId, user?.toVMLazy(), rating, showId)
    fun RatingShowVM.toModel() = RatingShow(ratingId, user?.toModel(), rating, showId)

    fun ShowShareUser.toVM() = ShowShareUserVM(id, username)
    fun ShowShareUser.toVMLazy() = ShowShareUserVM(id)
    fun ShowShareUserVM.toModel() = ShowShareUser(id, username)
    fun ShowShareRegisterUserVM.toModel() = ShowShareUser(id, username, password)

    fun WatchedEpisodeVM.toModel() = WatchedEpisode(viewDateId, showShareUser.toModel(), showId, seasonNumber, episodeNumber)
    fun WatchedEpisode.toVm() = WatchedEpisodeVM(viewDateId, showShareUser.toVMLazy(), showId, seasonNumber, episodeNumber)

    fun Watchlist.toVM() = WatchlistVM(watchlistId, showId, showShareUser.toVMLazy())
    fun WatchlistVM.toModel() = Watchlist(watchlistId, showId, showShareUserVM.toModel())
}