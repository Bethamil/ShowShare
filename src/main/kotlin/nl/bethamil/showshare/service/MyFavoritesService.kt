package nl.bethamil.showshare.service

import nl.bethamil.showshare.viewmodel.MyFavoritesVM


interface MyFavoritesService {
    fun findByUserIdAndShowId(show_id : Long, user_id : Long) : MyFavoritesVM?
    fun findByUserId(user_id : Long) : List<MyFavoritesVM>
    fun save(myFavoritesVM: MyFavoritesVM)
    fun deleteById(id: Long)
}