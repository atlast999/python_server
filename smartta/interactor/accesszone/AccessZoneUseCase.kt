package vn.com.vti.smartta.interactor.accesszone

import io.reactivex.rxjava3.core.Single
import vn.com.vti.common.domain.usecase.rx.SingleUseCase
import vn.com.vti.smartta.model.pojo.accesszone.*
import vn.com.vti.smartta.model.repository.ClientRepository
import javax.inject.Inject
import kotlin.random.Random

class AccessZoneUseCase @Inject constructor() :
    SingleUseCase<List<AccessZone>, AccessZoneRequest>() {

    @Inject
    lateinit var repository: ClientRepository

    override fun create(params: AccessZoneRequest?): Single<out List<AccessZone>> {
        return getData()
    }

    private fun getData(): Single<out List<AccessZone>> {

        val random = Random(System.currentTimeMillis().toInt())
        val accessLevel = arrayOf(
            PublicArea, AreaLevel2, AreaLevel3, AreaLevel4
        )
        val type = arrayOf(AccessPermission.ALLOW, AccessPermission.DENY)

        val locationList = mutableListOf<AccessRoom>()
        for (i in 1..4) {
            locationList.add(
                AccessRoom(
                    roomId = "DEVICE-15",
                    roomName = "IP01",
                    buildingName = "HL Building",
                    floorNo = "FL09",
                    type = type[random.nextInt(2)],
                    level = accessLevel[random.nextInt(4)]
                )
            )
        }
        return (0 until 4).map {
            AccessZone(
                buildingName = "HL Building",
                accessRoomList = locationList
            )
        }.toList().let {
            Single.just(it)
        }
    }
}