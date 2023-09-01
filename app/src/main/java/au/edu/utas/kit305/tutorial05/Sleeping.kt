package au.edu.utas.kit305.tutorial05

class Sleeping() {
    var id: String? = null
    var date: String? = null
    var startTime: String? = null
    var endTime: String? = null
    var remarks: String? = null

    constructor(
        id: String?,
        date: String?,
        startTime: String?,
        endTime: String?,
        remarks: String?,
    ) : this() {
        this.id = id
        this.date = date
        this.startTime = startTime
        this.endTime = endTime
        this.remarks = remarks
    }

}