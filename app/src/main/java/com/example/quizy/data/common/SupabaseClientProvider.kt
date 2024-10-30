package com.example.quizy.data.common

import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.realtime.Realtime
import io.github.jan.supabase.serializer.KotlinXSerializer
import io.github.jan.supabase.storage.Storage
import kotlinx.serialization.json.Json


object SupabaseClientProvider {

        val client =
            createSupabaseClient(
                supabaseUrl = "",
                supabaseKey = ""
            ){
                install(Postgrest)
                install(Realtime)
                install(Storage)

                defaultSerializer = KotlinXSerializer(Json {
                    prettyPrint = true
                    ignoreUnknownKeys = true
                })
            }




}
