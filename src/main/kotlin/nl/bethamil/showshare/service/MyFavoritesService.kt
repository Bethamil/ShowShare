package nl.bethamil.showshare.service

import nl.bethamil.showshare.viewmodel.MyFavoritesVM
import org.springframework.data.domain.Pageable


interface MyFavoritesService {
    fun findByUserIdAndShowId(show_id : Long, user_id : Long) : MyFavoritesVM?
    fun findByUserId(user_id : Long, pageable: Pageable =  Pageable.unpaged()) : List<MyFavoritesVM>
    fun save(myFavoritesVM: MyFavoritesVM)
    fun deleteById(id: Long)
    fun countMyFavoritesByUserId(user_id: Long) : Long

}