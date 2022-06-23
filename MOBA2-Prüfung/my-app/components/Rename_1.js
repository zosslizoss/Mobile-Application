import React, { useContext } from 'react';
import { Text, View, StyleSheet, Image } from 'react-native';

import Sneakers from '../sneakers/sneakers.json'

const Rename_1 = ({setWishlist, wishlist}) => {

  return (
    <View>
      <h1>Guten Tag Peter Muster</h1>
      <View style={styles.main}>
        {Sneakers.map(sneaker => (
          <View style={styles.sneakerItem} key={sneaker.productId}>
            <Image style={styles.sneakerImage} source={require("../sneakers/" + sneaker.image2)} alt={sneaker.altText} />
            <Text style={styles.sneakerName}>{sneaker.displayName}</Text>
            <Text>{sneaker.price} CHF</Text>
            {!wishlist.includes(sneaker) ?
              <Text onClick={() => setWishlist((wishlist) => [...wishlist, sneaker])}>&#9734;</Text>
            : <Text onClick={() => setWishlist(wishlist.filter((elem) => elem.productId != sneaker.productId))}>&#9733;</Text>}
          </View>
        ))}
      </View>
    </View>
  );
};

const styles = StyleSheet.create({
  sneakerItem: {
    flex: 1,
    justifyContent: 'space-between',
    backgroundColor: '#ecf0f1',
    marginBottom: 5,
    padding: 8,
    borderWidth: 1,
    borderColor: 'black',
    flexDirection:'row',
    alignItems:'center',
    alignSelf: 'stretch'
  },
  sneakerName: {
    width: 250
  },
  main: {
    marginBottom: 60
  },
  sneakerImage: {
    borderWidth: 1,
    borderColor: 'black',
    width: 40,
    height: 40
  }
});

export default Rename_1;
