//
//  DetailView.swift
//  KnowYourMusic
//
//  Created by Tobias Ritscher on 01.03.22.
//

import SwiftUI
import AVKit
import MusicKit

class SoundManager : ObservableObject {
    var audioPlayer: AVPlayer?
    @State var hasFinished = false

    func playSound(sound: String){
        if let url = URL(string: sound) {
            self.audioPlayer = AVPlayer(url: url)
        }
        
    }
}

struct DetailView: View {
    @State var item: Result
    @State var song1 = false
    @StateObject private var soundManager = SoundManager()
    @State var results: [Result]
    
    @State private var searchName = "Song"
    @State private var selection: String? = nil
    @State private var wrapperType = "song"
    @State private var loadingOne = false;
    @State private var loadingTwo = false;
    
    let titelId: Int?
    
    var body: some View {
        VStack {
            if item.wrapperType != "artist" {
                ZStack {
                    AsyncImage(url: URL(string: item.artworkUrl100 ?? "questionmark.circle.fill"))
                    { image in
                        image.resizable()
                    }
                    placeholder: {
                        ProgressView()
                    }.frame(width: 200, height: 200)
                    
                    if item.wrapperType == "track" {
                        Image(systemName: song1 ? "pause.circle.fill": "play.circle.fill")
                        .font(.system(size: 60))
                        .onTapGesture {
                            soundManager.playSound(sound: item.previewUrl ?? "missing info")
                            song1.toggle()
                            
                            if song1{
                                soundManager.audioPlayer?.play()
                            } else {
                                soundManager.audioPlayer?.pause()
                            }
                        }
                        
                    }
                }.padding()
            }
            Text(item.trackName ?? item.collectionName ?? item.artistName ?? "missing data")
                .bold().font(.system(size: 25))
            if item.wrapperType != "artist" {
                Text(item.artistName ?? "missing info").padding()
            }
            if item.wrapperType == "collection" {
                GroupBox {
                    List(results.indices, id: \.self) { index in
                        NavigationLink(destination: DetailView(item: item, results: [], titelId: results[index].trackId!)) {
                            VStack(alignment: .leading) {
                                Text("\(index + 1)   \(results[index].trackName ?? "missing data")")
                            }
                        }
                    }
                }
            } else if item.wrapperType == "artist" {
                NavigationLink(destination: SearchResults(results: $results, searchType: $searchName, wrapperType: wrapperType), tag: "results", selection: $selection) { EmptyView() }
                
                HStack {
                    Button("Show Titels of \(item.artistName!)"){
                        loadingOne = true;
                        wrapperType = "track"
                        search(id: item.artistId!, entity: "song")
                        results.remove(at: 0)
                        searchName = "Song"
                    }.padding()
                    if loadingOne {
                        ProgressView()
                    }
                }
                
                HStack {
                    Button("Show Albums of \(item.artistName!)"){
                        loadingTwo = true;
                        wrapperType = "collection"
                        search(id: item.artistId!, entity: "album")
                        results.remove(at: 0)
                        searchName = "Album"
                    }.padding()
                    if loadingTwo {
                        ProgressView()
                    }
                }
            }
        }
        .navigationTitle((item.trackName ?? item.collectionName ?? item.artistName ?? "missing data").split(separator: "(")[0]).padding()
        .onAppear() {
            loadingOne = false;
            loadingTwo = false;
            results.removeAll()
            Task.init {
                if titelId != nil {
                    await getTitel(id: titelId!)
                } else {
                    if item.wrapperType == "collection" {
                        if await loadData(id: item.collectionId!, entity: "song") {
                            print("success")
                            if results.count > 0 && results[0].trackName == nil {
                                results.remove(at: 0)
                            }
                        } else {
                            print("failed")
                        }
                    } else if item.wrapperType == "artist" {
                        if await loadData(id: item.artistId!, entity: "album") {
                            print("success")
                            if results.count > 0 && results[0].trackName == nil {
                                results.remove(at: 0)
                            }
                        } else {
                            print("failed")
                        }
                    }
                }
            }
        }
    }
    
    func search(id: Int, entity: String) {
        Task.init {
            if await loadData(id: id, entity: entity) {
                selection = "results"
            }
            
        }
    }
    
    func getTitel(id: Int) async {
        if await loadData(id: id, entity: "song") {
            print("success")
            if results.count > 0 && results[0].trackName == nil {
                results.remove(at: 0)
            }
        } else {
            print("failed")
        }
        item = results[0]
    }
    
    func loadData(id: Int, entity: String) async -> Bool {
        guard let url = URL(string: "https://itunes.apple.com/lookup?id=\(id)&entity=\(entity)&limit=200") else {
            print("Invalid URL")
            return false
        }
        do {
            print("loading data")
            let (data, _) = try await URLSession.shared.data(from: url)
            
            if let decodedResponse = try? JSONDecoder().decode(Response.self, from: data) {
                results = decodedResponse.results
            }
            return true
        } catch {
            print("Invalid data")
            return false
        }
    }
}
