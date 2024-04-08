import 'react-native-devsettings';
import React, {useEffect} from 'react';
import {StyleSheet, Text, View} from 'react-native';

export default function App() {
  const stepInto = () => {
    console.log('Stepped into');
  };

  const stepOver = () => {
    console.log('Stepped Over');
  };

  useEffect(() => {
    console.log('Pause here and stop');
    stepOver();
    stepInto();
  }, []);

  console.log('App executed');

  return (
    <View style={styles.container}>
      <Text>Hello World!!!</Text>
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#fff',
    alignItems: 'center',
    justifyContent: 'center',
  },
});
