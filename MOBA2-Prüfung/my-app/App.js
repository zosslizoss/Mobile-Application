import React, { useState } from 'react'
import { StyleSheet, Text, View, Button } from 'react-native';

import Rename_1 from './components/Rename_1';
import Rename_2 from './components/Rename_2';

export default function App() {
  const [component, setComponent] = useState("Rename_1_STATE");
  const [wishlist, setWishlist] = useState([]);

  return (
    <View style={styles.container}>
      {
        (component == "Rename_1_STATE") ?
        <Rename_1 setWishlist={setWishlist} wishlist={wishlist}/> :
        <Rename_2 setWishlist={setWishlist} wishlist={wishlist}/>
      }
      <View style={styles.tabs}>
        <View style={styles.tab} onClick={() => setComponent("Rename_1_STATE")}>
          <Text style={styles.tabText}>Produkte</Text>
        </View>
        <View style={styles.tab} onClick={() => setComponent("Rename_2_STATE")}>
          <Text style={styles.tabText}>Wunschliste</Text>
        </View>
      </View>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
    alignItems: 'center',
    textAlign: 'center',
    justifyContent: 'center',
  },
  tabText: {
    alignItems: 'center',
    textAlign: 'center',
    justifyContent: 'center',
    padding: 15,
    fontSize: 20
  },
  tabs: {
    flex: 1,
    justifyContent: 'space-between',
    backgroundColor: '#ecf0f1',
    flexDirection:'row',
    alignSelf: 'stretch',
    width: '100%',
    position: 'fixed',
    bottom: 0,
    height: 60
  },
  tab: {
    borderWidth: 1,
    alignItems:'center',
    borderColor: 'black',
    backgroundColor: '#ecf0f1',
    width: '50%'
  }
});
