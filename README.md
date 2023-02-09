# Growable Crystals
Minecraft mod that adds growable crystals for the different ore types.
Currently in the very early stages of development.

## Features
- Growable crystals for each ore type
  - Different number of growth stages per crystal
  - Different time to grow time per crystal
- Crystal powder item
  - Custom item entity controls crystal block spawning in water

## Steps to growing your own crystals
- Craft crystal powder for desired ore
- Throw powder into water
- Crystals will grow on corresponding ore blocks adjacent to the water
- Crystals must be waterlogged to continue growing

## Iteration 1 (Complete)
- Create crystal block & item
  - Can be placed directionally
  - Can be waterlogged

## Iteration 2 (Complete)
- Crystals grow through different stages, only while waterlogged.
- Particle effects when growing.
- Use "seed crystals" to start growing crystals.
  - Crystal type is determined by ore it is placed on
- Largest stage drops item when broken.

## Iteration 3 (Complete)
- Various stages drop differing amounts of loot.
- Crystals have appropriate bounding box for growth stage and direction.
- Crystals grow from crystal powder item entity in water
- Implement more crystal types
  - Redstone, Coal, Iron, Gold, Lapis, Diamond, Copper, Emerald

## Iteration 4 (Current)
- Use random walk to determine crystal placement from item entity
  - Instead of random x,y,z offset from position (can clip through walls)
- Implement a way for players to obtain crystal powder.
- Add small/crushed crystal drops for corresponding material.

## Exploration
- Crystals can grow on multiple block faces
  - Create blocktype composing crystal faces (look at vines)
  - Dynamic bounding box rendering and interaction per crystal growth?
- Crystal powder item entity spawns particles, which in turn spawn crystals on blocks.
