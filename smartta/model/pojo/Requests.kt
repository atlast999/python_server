package vn.com.vti.smartta.model.pojo

fun interface IParamRequests {

    fun buildInto(bundle: MutableMap<String, String>)
}

fun IParamRequests.build(): MutableMap<String, String> =
    hashMapOf<String, String>().also {
        this.buildInto(it)
    }