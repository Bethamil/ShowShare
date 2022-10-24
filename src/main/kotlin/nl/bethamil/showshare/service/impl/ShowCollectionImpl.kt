package nl.bethamil.showshare.service.impl

import nl.bethamil.showshare.model.ShowCollection
import nl.bethamil.showshare.repository.ShowCollectionRepo
import nl.bethamil.showshare.service.ShowCollectionService
import org.springframework.stereotype.Service
import java.util.*

@Service
class ShowCollectionImpl(val showCollectionRepo: ShowCollectionRepo) : ShowCollectionService{
    override fun findByUserIdAndShowId(show_id: Long, user_id: Long): Optional<ShowCollection> {
        return showCollectionRepo.findByUserIdAndShowId(show_id, user_id)
    }

    override fun findByUserId(user_id: Long): List<ShowCollection> {
        return showCollectionRepo.findByUserId(user_id)
    }

    override fun save(showCollection: ShowCollection) {
        showCollectionRepo.save(showCollection)
    }

    override fun delete(showCollection: ShowCollection) {
        showCollectionRepo.delete(showCollection)
    }
}