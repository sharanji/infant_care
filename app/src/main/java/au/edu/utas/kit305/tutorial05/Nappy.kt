package au.edu.utas.kit305.tutorial05

class Nappy() {
    var id: String? = null
    var date: String? = null
    var startTime: String? = null
    var endtTime: String? = null
    var ntcount: String? = null
    var remarks: String? = null
    var type: String? = null


    constructor(id: String?, date: String?, startTime: String?, endtTime: String?, ntcount: String?, remarks: String?, type: String?) : this() {
        this.id = id
        this.date = date
        this.startTime = startTime
        this.endtTime = endtTime
        this.ntcount = ntcount
        this.remarks = remarks
        this.type = type

    }
}