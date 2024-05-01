/*
Copyright 2024 Matheus Menezes

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */

package br.com.mathsemilio.simpleapodbrowser.util

import androidx.annotation.VisibleForTesting
import br.com.mathsemilio.simpleapodbrowser.domain.model.Apod

@VisibleForTesting(otherwise = VisibleForTesting.NONE)
val latestApods = listOf(
    Apod(
        title = "Swirling Magnetic Field around Our Galaxy's Central Black Hole",
        url = "https://apod.nasa.gov/apod/image/2404/SagAstarB_EHT_960.jpg",
        date = "2024-04-01",
        mediaType = "image",
        explanation = "What's happening to the big black hole in the center of our galaxy?  It is sucking in matter from a swirling disk -- a disk that is magnetized, it has now been confirmed. Specifically, the black hole's accretion disk has recently been seen to emit polarized light, radiation frequently associated with a magnetized source. Pictured here is a close-up of Sgr A*, our Galaxy's central black hole, taken by radio telescopes around the world participating in the Event Horizon Telescope (EHT) Collaboration.  Superposed are illustrative curved lines indicating polarized light likely emitted from swirling magnetized gas that will soon fall into the 4+ million solar mass central black hole.  The central part of this image is likely dark because little light-emitting gas is visible between us and the dark event horizon of the black hole.  Continued EHT monitoring of this and M87's central black hole may yield new clues about the gravity of black holes and how infalling matter creates disks and jets.    NASA Predicts:  Moon to Get in Way of Sun",
    ),
    Apod(
        title = "Detailed View of a Solar Eclipse Corona",
        url = "https://apod.nasa.gov/apod/image/2404/CoronaExmouth_Hart_1080.jpg",
        date = "2024-04-02",
        mediaType = "image",
        explanation = "Only in the fleeting darkness of a total solar eclipse is the light of the solar corona easily visible. Normally overwhelmed by the bright solar disk, the expansive corona, the sun's outer atmosphere, is an alluring sight. But the subtle details and extreme ranges in the corona's brightness, although discernible to the eye, are notoriously difficult to photograph. Pictured here, however, using multiple images and digital processing, is a detailed image of the Sun's corona taken during the April 20, 2023 total solar eclipse from Exmouth, Australia. Clearly visible are intricate layers and glowing caustics of an ever changing mixture of hot gas and magnetic fields. Bright looping prominences appear pink just around the Sun's limb. A similar solar corona might be visible through clear skies in a narrow swath across the North America during the total solar eclipse that occurs just six days from today  NASA Coverage: Total Solar Eclipse of 2024 April 8",
        copyright = "\nPhil Hart\n"
    ),
    Apod(
        title = "Unusual Nebula Pa 30",
        url = "https://apod.nasa.gov/apod/image/2404/Pa30V_NASA_960.jpg",
        date = "2024-04-03",
        mediaType = "image",
        explanation = "What created this unusual celestial firework? The nebula, dubbed Pa 30, appears in the same sky direction now as a bright \"guest star\" did in the year 1181. Although Pa 30's filaments look similar to that created by a nova (for example GK Per), and a planetary nebula (for example NGC 6751), some astronomers now propose that it was created by a rare type of supernova: a thermonuclear Type Iax, and so is (also) named SN 1181.  In this model, the supernova was not the result of the detonation of a single star, but rather a blast that occurred when two white dwarf stars spiraled together and merged.  The blue dot in the center is hypothesized to be a zombie star, the remnant white dwarf that somehow survived this supernova-level explosion.  The featured image combines images and data obtained with infrared (WISE), visible  (MDM, Pan-STARRS), and X-ray (Chandra, XMM) telescopes.  Future observations and analyses may tell us more.   NASA Coverage: Total Solar Eclipse of 2024 April 8",
    ),
    Apod(
        title = "Comet Pons-Brooks at Night",
        url = "https://apod.nasa.gov/apod/image/2404/12P_Pons_Brooks_2024_03_30_JuneLake_DEBartlett1024.jpg",
        date = "2024-04-04",
        mediaType = "image",
        explanation = "In dark evening skies over June Lake, northern hemisphere, planet Earth, Comet 12P/Pons-Brooks stood just above the western horizon on March 30. Its twisted turbulent ion tail and diffuse greenish coma are captured in this two degree wide telescopic field of view along with bright yellowish star Hamal also known as Alpha Arietis. Now Pons-Brooks has moved out of the northern night though, approaching perihelion on April 21. On April 8 you might still spot the comet in daytime skies. But to do it, you will have to stand in the path of totality and look away from the spectacle of an alluring solar corona and totally eclipsed Sun.   NASA Coverage: Total Solar Eclipse of 2024 April 8",
        copyright = "Dan Bartlett"
    ),
    Apod(
        title = "The Solar Corona Unwrapped",
        url = "https://apod.nasa.gov/apod/image/2404/CoronaGraph_1024.jpg",
        date = "2024-04-05",
        mediaType = "image",
        explanation = "Changes in the alluring solar corona are detailed in this creative composite image mapping the dynamic outer atmosphere of the Sun during two separate total solar eclipses. Unwrapped from the complete circle of the eclipsed Sun's edge to a rectangle and mirrored, the entire solar corona is shown during the 2017 eclipse (bottom) seen from Jackson Hole, Wyoming, and the 2023 eclipse from Exmouth, Western Australia. While the 2017 eclipse was near a minimum in the Sun's 11 year activity cycle, the 2023 eclipse was closer to solar maximum. The 2023 solar corona hints at the dramatically different character of the active Sun, with many streamers and pinkish prominences arising along the solar limb. Of course, the solar corona is only easily visible to the eye while standing in the shadow of the Moon.   NASA Coverage: Total Solar Eclipse of 2024 April 8",
        copyright = "Barden Ridge Observatory"
    ),
    Apod(
        title = "Unwinding M51",
        url = "https://apod.nasa.gov/apod/image/2404/EclipseWyoming_Cooper_960.jpg",
        date = "2024-04-07",
        mediaType = "image",
        explanation = "Will the sky be clear enough to see the eclipse? This question is already on the minds of many North Americans hoping to see tomorrow's solar eclipse.  This question was also on the mind of many people attempting to see the total solar eclipse that crossed North America in August 2017.  Then, the path of total darkness shot across the mainland of the USA from coast to coast, from Oregon to South Carolina -- but, like tomorrow's event, a partial eclipse occurred above most of North America.  Unfortunately, in 2017, many locations saw predominantly clouds. One location that did not was a bank of the Green River Lakes, Wyoming.  Intermittent clouds were far enough away to allow the center image of the featured composite sequence to be taken, an image that shows the corona of the Sun extending out past the central dark Moon that blocks our familiar Sun. The surrounding images show the partial phases of the solar eclipse both before and after totality.   NASA Coverage: Tomorrow's Total Solar Eclipse",
        copyright = "\nBen Cooper\n"
    ),
    Apod(
        title = "The Changing Ion Tail of Comet Pons-Brooks",
        url = "https://apod.nasa.gov/apod/image/2404/Comet12pTails_ShengyuLi_960.jpg",
        date = "2024-04-08",
        mediaType = "image",
        explanation = "How does a comet tail change? It depends on the comet.  The ion tail of Comet 12P/Pons–Brooks has been changing markedly, as detailed in the featured image sequenced over nine days from March 6 to 14 (top to bottom).  On some days, the comet's ion tail was relatively long and complex, but not every day.  Reasons for tail changes include the rate of ejection of material from the comet's nucleus, the strength and complexity of the passing solar wind, and the rotation rate of the comet.  Over the course of a week, apparent changes even include a change of perspective from the Earth. In general, a comet's ion tail will point away from the Sun, as gas expelled is pushed out by the Sun's wind. Today, Pons-Brooks may become a rare comet suddenly visible in the middle of the day for those able to see the Sun totally eclipsed by the Moon.   NASA Coverage: Today's Total Solar Eclipse  Total Eclipse Imagery: Notable Submissions to APOD",
        copyright = "\nShengyu Li & Shaining\n"
    )
)
