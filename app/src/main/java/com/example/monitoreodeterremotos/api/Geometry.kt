package com.example.monitoreodeterremotos.api;

public class Geometry(private val coordinates:Array<Double>){
    val longitu:Double get() = coordinates[0]
    val latitud:Double get() = coordinates[1]
}
