class Tarefa {
    var id: String? = null
    var uid: String? = null
    var titulo: String? = null
    var data: String? = null

    constructor()
    constructor(id: String?, uid: String?, titulo: String?, data: String?) {
        this.id = id
        this.uid = id
        this.titulo = titulo
        this.data = data
    }
}
