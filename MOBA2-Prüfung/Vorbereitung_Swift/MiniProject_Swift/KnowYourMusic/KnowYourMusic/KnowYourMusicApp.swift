
//
//  Created by Tobias Ritscher on 22.02.22.
//  Approved by Joël Plambeck on 03.05.22. :-)
//

import SwiftUI

@main
struct KnowYourMusicApp: App {
    let persistenceController = PersistenceController.shared
    @State var results = [Result]()
    @State var resultCount = -1

    var body: some Scene {
        WindowGroup {
            SearchView(results: $results, resultCount: $resultCount)

        }
    }
}
