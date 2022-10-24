package nl.bethamil.showshare.service

import nl.bethamil.showshare.model.ShowCollection
import java.util.Optional


interface ShowCollectionService {
    fun findByUserIdAndShowId(show_id : Long, user_id : Long) : Optional<ShowCollection>
    fun findByUserId(user_id : Long) : List<ShowCollection>
    fun save(showCollection: ShowCollection)
    fun delete(showCollection: ShowCollection)
}