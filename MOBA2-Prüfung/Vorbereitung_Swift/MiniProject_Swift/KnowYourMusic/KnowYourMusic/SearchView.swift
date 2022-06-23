//
//  SearchView.swift
//  KnowYourMusic
//
//  Created by Tobias Ritscher on 25.02.22.
//

import SwiftUI

struct SearchView: View {
    @State private var searchValue: String = ""
    @State private var infoText = ""
    @State private var textFieldValue = "type here..."
    @State private var selection: String? = nil
    @State private var searchName = "Choose one"
    @State private var searchType = "songTerm"
    @State private var searchEntity = "musicTrack"
    @State private var groupIsExpanded = false
    @State private var wrapperType = "song"
    
    @Binding var results: [Result]
    @Binding var resultCount: Int

    var songSearch = searchKeys(name: "Song", value:"songTerm", entity: "song")
    var albumSearch = searchKeys(name: "Album", value:"albumTerm", entity: "album")
    var interpretSearch = searchKeys(name: "Interpret", value:"artistTerm", entity: "musicArtist")
    
    var body: some View {
        NavigationView{
            VStack{
                NavigationLink(destination: SearchResults(results: $results, searchType: $searchName, wrapperType: wrapperType), tag: "results", selection: $selection) { EmptyView() }
                
                HStack {
                    Text("Search Type: ")
                    GroupBox {
                        DisclosureGroup(searchName, isExpanded: $groupIsExpanded) {
                            Button(songSearch.name){
                                changeSearchTerms(terms: songSearch)
                            }.foregroundColor(Color.secondary)
                            Button(albumSearch.name){
                                changeSearchTerms(terms: albumSearch)
                            }.foregroundColor(Color.secondary).padding(.vertical, 5)
                            Button(interpretSearch.name){
                                changeSearchTerms(terms: interpretSearch)
                            }.foregroundColor(Color.secondary)
                        }
                    }.padding(.vertical, 10).background(Color.clear).foregroundColor(Color.secondary)
                }
                
                HStack {
                    Text("Keyword: ")
                    HStack {
                        TextField(textFieldValue, text: $searchValue).onSubmit {
                            search()
                        }

                        if searchValue.count > 0 {
                            Image(systemName: "x.circle.fill")
                                .font(.system(size: 17))
                                .onTapGesture {
                                    searchValue = ""
                                }
                        }
                        
                    }
                }.padding()
                
                Button("Search"){
                    search()
                }
                .padding(.vertical, 10)
                
                Text(infoText)
                if infoText == "loading...." {
                    ProgressView()
                }
            }.padding().task {
                infoText = ""
            }
        }
    }
    
    func changeSearchTerms(terms: searchKeys) {
        searchType = terms.value
        searchName = terms.name
        searchEntity = terms.entity
        groupIsExpanded = false
    }
    
    func search() {
        infoText = "loading...."
        Task.init {
            if await loadData() {
                if results.count > 0 {
                    wrapperType = results[0].wrapperType!
                    selection = "results"
                    print("Wrapper Type: \(wrapperType)")
                } else {
                    infoText = "No Results found :("
                }
                print(resultCount)
            } else {
                infoText = "failed: wrong URL or invalid Data"
            }
        }
    }
    
    
func loadData() async -> Bool {
    let searchValueCleaned = clearSearch(searchValue: searchValue)
    guard let url = URL(string: "https://itunes.apple.com/search?term=\(searchValueCleaned)&country=ch&media=music&attribute=\(searchType)&entity=\(searchEntity)") else {
        print("Invalid URL")
        return false
    }
    do {
        print("loading data")
        let (data, _) = try await URLSession.shared.data(from: url)
        
        if let decodedResponse = try? JSONDecoder().decode(Response.self, from: data) {
            results = decodedResponse.results
            resultCount = decodedResponse.resultCount
        }
        return true
    } catch {
        print("Invalid data")
        return false
    }
}
    
    func clearSearch(searchValue: String) -> String {
        searchValue.replacingOccurrences(of: " ", with: "+")
            .replacingOccurrences(of: "ö", with: "o")
            .replacingOccurrences(of: "ä", with: "a")
            .replacingOccurrences(of: "ü", with: "u")
    }
}

struct searchKeys {
    var name: String
    var value: String
    var entity: String
}

struct Response: Codable {
    var results: [Result]
    var resultCount: Int
}

struct Result: Codable {
    var wrapperType: String!
    var artistId: Int!
    var trackId: Int?
    var trackName: String?
    var collectionName: String?
    var artworkUrl60: String?
    var artworkUrl100: String?
    var previewUrl: String?
    var artistName: String?
    var trackViewUrl: String?
    var collectionId: Int?
    var trackCount: Int?
    var releaseDate: String?
    var primaryGenreName: String?
}
