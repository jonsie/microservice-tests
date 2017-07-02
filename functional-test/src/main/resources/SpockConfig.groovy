import us.gr8conf.tags.ProdSmoke

runner {
    if (Boolean.getBoolean('prodsmoke')) {
        include.annotations << ProdSmoke
    } else {
        exclude.annotations << ProdSmoke
    }
}
