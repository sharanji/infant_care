package au.edu.utas.kit305.tutorial05


class Feeding() {
    var id: String? = null
    var date: String? = null
    var startTime: String? = null
    var endtTime: String? = null
    var btcount: String? = null
    var remarks: String? = null
    var type: String? = null
    var side: String? = null

    constructor(id: String?, date: String?, startTime: String?, endtTime: String?, btcount: String?, remarks: String?, type: String?, side: String?) : this() {
        this.id = id
        this.date = date
        this.startTime = startTime
        this.endtTime = endtTime
        this.btcount = btcount
        this.remarks = remarks
        this.type = type
        this.side = side
    }
}