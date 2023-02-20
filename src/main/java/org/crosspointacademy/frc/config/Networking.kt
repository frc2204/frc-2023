package org.crosspointacademy.frc.config

import org.crosspointacademy.lib.NetworkingModule

object Networking {

    object Limelight3 : NetworkingModule() {
        override val hostname = "limelight-three"
        override val forwardingPorts: List<Int> = IntRange(5800, 5805).toList() + 5807
    }


}