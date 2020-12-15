package vn.com.vti.smartta.ui.dialog.selection.adapter

interface ISingleChoice {

    fun getId(): Int

    fun getLabel(): String
}

data class SingleChoice(private val _id: Int, private val _label: String) : ISingleChoice {

    override fun getId() = _id

    override fun getLabel() = _label

}