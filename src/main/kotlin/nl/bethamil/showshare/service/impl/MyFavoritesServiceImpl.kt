package nl.bethamil.showshare.service.impl

import nl.bethamil.showshare.mapper.ModelViewMapper
import nl.bethamil.showshare.repository.MyFavoritesRepo
import nl.bethamil.showshare.service.MyFavoritesService
import nl.bethamil.showshare.viewmodel.MyFavoritesVM
import org.springframework.stereotype.Service

@Service
class MyFavoritesServiceImpl(val myFavoritesRepo: MyFavoritesRepo) : MyFavoritesService, ModelViewMapper{
    override fun findByUserIdAndShowId(show_id: Long, user_id: Long): MyFavoritesVM? {
        val myFavorites =  myFavoritesRepo.findByUserIdAndShowId(show_id, user_id)
        if (myFavorites.isPresent) {
            return myFavorites.get().toVM()
        }
        return null
    }

    override fun findByUserId(user_id: Long): List<MyFavoritesVM> {
        return myFavoritesRepo.findByUserId(user_id).map { it.toVM()}
    }

    override fun save(myFavoritesVM: MyFavoritesVM) {
        myFavoritesRepo.save(myFavoritesVM.toModel())
    }

    override fun deleteById(id : Long) {
        myFavoritesRepo.deleteById(id)
    }
}