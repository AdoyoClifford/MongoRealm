package com.adoyo.mongodbrealm.data.repository

import com.adoyo.mongodbrealm.model.Address
import com.adoyo.mongodbrealm.model.Person
import com.adoyo.mongodbrealm.model.Pet
import com.adoyo.mongodbrealm.utils.Constants.APP_ID
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import io.realm.kotlin.log.LogLevel
import io.realm.kotlin.mongodb.App
import io.realm.kotlin.mongodb.sync.SyncConfiguration
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import org.mongodb.kbson.ObjectId

object MongoDB : MongoRepository {
    private val app = App.create(APP_ID)
    private val user = app.currentUser
    private lateinit var realm: Realm

    init {

    }

    override fun configureTheRealm() {
        if (user != null) {
            val config = SyncConfiguration.Builder(
                user, setOf(Person::class, Address::class, Pet::class)
            )
                .initialSubscriptions { sub ->
                    add(query = sub.query<Person>(query = "owner_id == $0", user.id))
                }
                .log(LogLevel.ALL)
                .build()
            realm = Realm.open(config)
        }
    }

    override fun getData(): Flow<List<Person>> {
        return realm.query<Person>().asFlow().map { it.list }
    }

    override fun filterData(name: String): Flow<List<Person>> {
        return realm.query<Person>(query = "name CONTAINS[c] $0",name).asFlow().map { it.list }
    }

    override suspend fun insertPerson(person: Person) {
        TODO("Not yet implemented")
    }

    override suspend fun updatePerson(person: Person) {
        TODO("Not yet implemented")
    }

    override suspend fun deletePerson(id: ObjectId) {
        TODO("Not yet implemented")
    }
}