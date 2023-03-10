package com.adoyo.mongodbrealm.data.repository

import com.adoyo.mongodbrealm.model.Person
import kotlinx.coroutines.flow.Flow
import org.mongodb.kbson.ObjectId

interface MongoRepository {
    fun configureTheRealm()
    fun getData(): Flow<List<Person>>
    fun filterData(name: String): Flow<List<Person>>
    suspend fun insertPerson(person: Person)
    suspend fun updatePerson(person: Person)
    suspend fun deletePerson(id: ObjectId)

}