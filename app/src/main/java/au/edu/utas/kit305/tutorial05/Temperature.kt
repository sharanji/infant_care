package au.edu.utas.kit305.tutorial05

class Temperature() {
    var id: String? = null
    var date: String? = null
    var btcount: String? = null
    var remarks: String? = null


    constructor(id: String?, date: String?, btcount: String?, remarks: String?) : this() {
        this.id = id
        this.date = date
        this.btcount = btcount
        this.remarks = remarks
    }
}