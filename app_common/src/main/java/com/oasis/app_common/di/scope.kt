package com.oasis.app_common.di

import org.koin.core.qualifier.Qualifier
import org.koin.core.scope.Scope

// ScopeOwner.kt
interface ScopeOwner {
    val scope: Scope
    val scopeId: String
}

// ScopeProvider.kt
interface ScopeProvider {
    fun getOrCreateScope(id: String, qualifier: Qualifier? = null): Scope
}