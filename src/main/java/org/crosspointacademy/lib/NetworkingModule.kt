package org.crosspointacademy.lib

import edu.wpi.first.net.PortForwarder

abstract class NetworkingModule {

    abstract val hostname: String
    abstract val forwardingPorts: List<Int>

    private val fullHostname get() = "$hostname.local"

    fun addForwardingPorts() {
        forwardingPorts.forEach { port -> PortForwarder.add(port, fullHostname, port) }
    }

}