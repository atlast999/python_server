package vn.com.vti.smartta.interactor.notification

import io.reactivex.rxjava3.core.Single
import vn.com.vti.common.domain.usecase.rx.SingleUseCase
import vn.com.vti.smartta.model.pojo.notification.Notification
import vn.com.vti.smartta.model.pojo.notification.NotificationType
import javax.inject.Inject

class GetNotificationUseCase @Inject constructor() :
    SingleUseCase<List<Notification>, Unit>() {

    override fun create(params: Unit?): Single<out List<Notification>> {
        return Single.defer {
            listOf(
                Notification(
                    1,
                    "Thông báo nghỉ lễ",
                    "Cả công ty được nghỉ ngày 23-11",
                    NotificationType.NORMAL.value
                ),
                Notification(
                    2,
                    "Yêu cầu nghỉ phép",
                    "Nguyễn Văn A nghỉ phép ngày 23-11",
                    NotificationType.REQUEST.value
                ),
                Notification(
                    3,
                    "Giải bóng đá nam VTI 2020",
                    "Thông báo tới toàn thể các đơn vị!",
                    NotificationType.NORMAL.value
                ),
                Notification(
                    4,
                    "Yêu cầu nghỉ phép",
                    "Nguyễn Văn B nghỉ phép ngày 22-11",
                    NotificationType.REQUEST.value
                ),
                Notification(
                    5,
                    "Chúc mừng ngày nhà giáo Việt Nam 20-11!",
                    "\"The mediocre teacher tells. The good teacher explains. The superior teacher demonstrates. The great teacher inspires.\" - William Arthur Ward -",
                    NotificationType.NORMAL.value
                ),
                Notification(
                    6,
                    "Yêu cầu nghỉ phép",
                    "Nguyễn Mạnh C nghỉ phép ngày 19-11",
                    NotificationType.REQUEST.value
                ),
                Notification(
                    7,
                    "Yêu cầu nghỉ phép",
                    "Nguyễn Đức D nghỉ phép 19-11",
                    NotificationType.REQUEST.value
                ),
                Notification(
                    8,
                    "Yêu cầu nghỉ phép",
                    "Nguyễn E nghỉ phép ngày 18-11",
                    NotificationType.REQUEST.value
                ),
                Notification(
                    9,
                    "Tổ chức Team Building lần 2 2020",
                    "Xin chào cả nhà, dưới đây là 1 số thông tin về chương trình tổ chức teambuilding lần 2_2020",
                    NotificationType.NORMAL.value
                ),
                Notification(
                    10,
                    "Chuyển 2 lần lương cho một số nhân viên",
                    "Do tiếp tục là sự cố của TPBank nên lương tháng 10 đã được chuyển 02 lần cho một số nhân viên trong công ty.\n",
                    NotificationType.NORMAL.value
                ),
                Notification(
                    11,
                    "Chung tay khắc phục hậu quả sau lũ",
                    "Văn phòng công ty kết hợp với CLB Thiện nguyện VTI xin gửi thông tin tới toàn thể nhân viên về kế hoạch từ thiện của VTI Group sau nhiều trăn trở và thảo luận như sau:",
                    NotificationType.NORMAL.value
                )
            ).let {
                Single.just(it)
            }
        }
    }

}