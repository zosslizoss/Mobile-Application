//
//  SearchResults.swift
//  KnowYourMusic
//
//  Created by Tobias Ritscher on 01.03.22.
//

import SwiftUI

struct SearchResults: View {
    @Binding var results: [Result]
    @Binding var searchType: String
    @State var wrapperType: String
    
    var body: some View {
        if wrapperType == "collection" {
            List(results, id: \.collectionId) { item in
                if item.wrapperType == wrapperType {
                    NavigationLink(destination: DetailView(item: item, results: [], titelId: nil)) {
                        HStack {
                            AsyncImage(url: URL(string: item.artworkUrl100 ?? "questionmark.circle.fill"))
                            { image in
                                image.resizable()
                            } placeholder: {
                                ProgressView()
                            }.frame(width: 60, height: 60)
                            VStack(alignment: .leading) {
                                Text(item.collectionName ?? "missing data").font(.headline)
                                Text(item.artistName ?? "missing data")
                                Text("\(stringToDate(dateString: item.releaseDate ?? ""))   Tracks: \(item.trackCount ?? 0)")
                            }
                        }
                    }
                }
            }.navigationTitle("\(searchType)s (\(results.count))")
        } else if wrapperType == "artist" {
            List(results, id: \.artistId) { item in
                if item.wrapperType == wrapperType {
                    NavigationLink(destination: DetailView(item: item, results: [], titelId: nil)) {
                        VStack(alignment: .leading) {
                            Text(item.artistName ?? "missing data").font(.headline)
                            Text(item.primaryGenreName ?? "missing data")
                        }
                    }
                }
            }.navigationTitle("\(searchType)s (\(results.count))")
        } else {
            List(results, id: \.trackId) { item in
                if item.wrapperType == wrapperType {
                    NavigationLink(destination: DetailView(item: item, results: [], titelId: nil)) {
                        HStack {
                            AsyncImage(url: URL(string: item.artworkUrl100 ?? "https://upload.wikimedia.org/wikipedia/commons/3/33/White_square_with_question_mark.png"))
                            { image in
                                image.resizable()
                            } placeholder: {
                                ProgressView()
                            }.frame(width: 60, height: 60)
                            VStack(alignment: .leading) {
                                Text(item.trackName!).font(.headline)
                                Text(item.artistName!)
                                Text(item.collectionName!)
                            }
                        }
                    }
                }
            }.navigationTitle("\(searchType)s (\(results.count))")
        }
    }
    
    func stringToDate(dateString: String) -> String {
        let dateFormatter = DateFormatter()
        dateFormatter.dateFormat = "yyyy'-'MM'-'dd'T'HH':'mm':'ssZZZ"
        let date = dateFormatter.date(from: dateString) ?? Date.now
        dateFormatter.dateFormat = "dd'.'MM'.'yyyy"
        
        return dateFormatter.string(from: date)
    }
}
