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
                supabaseUrl = "https://uhwmyudboqxufkvwqoxf.supabase.co",
                supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6InVod215dWRib3F4dWZrdndxb3hmIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MjYwNzc2NjEsImV4cCI6MjA0MTY1MzY2MX0.HkBUFhz-3LNCAi9y3XzK3F1nekg1HrpJC3fo1bp4T0k"
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